package com.ampletec.boot.netty.server.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SecurityXMLServer implements Runnable {
	private ServerSocket server;
	private BufferedReader reader;
	private BufferedWriter writer;
	private String xml;

	public static void main(String[] args) {
		new SecurityXMLServer(843);
	}

	public SecurityXMLServer(int policyport) {
		xml = "<cross-domain-policy>\n" + "<allow-access-from domain=\"*\" to-ports=\"*\"/>\n" + "</cross-domain-policy>";
		// 启动843端口
		createServerSocket(policyport);
		new Thread(this).start();
	}

	// 启动服务器
	private void createServerSocket(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("SecurityXMLServer 安全沙箱服务监听端口：" + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean isConnected(Socket client) {
		try {
			client.sendUrgentData(0xFF);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 启动服务器线程
	public void run() {
		while (true) {
			Socket client = null;
			try {
				// 接收客户端的连接
				client = server.accept();
				client.setSoTimeout(0);
				System.out.println(client.getInetAddress() + " request 843");
				InputStreamReader input = new InputStreamReader(client.getInputStream(), "UTF-8");
				reader = new BufferedReader(input);
				OutputStreamWriter output = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
				writer = new BufferedWriter(output);

				// 读取客户端发送的数据
				StringBuilder data = new StringBuilder();
				int c = 0;
				while ((c = reader.read()) != -1) {
					if (c != '\0')
						data.append((char) c);
					else
						break;
				}
				String info = data.toString();
				System.out.println("输入的请求: " + info);

				// 接收到客户端的请求之后，将策略文件发送出去
				if (info.indexOf("<policy-file-request/>") >= 0) {
					writer.write(xml + "\0");
					writer.flush();
					System.out.println("将安全策略文件发送至: " + client.getInetAddress());
				} else {
					writer.write("请求无法识别\0");
					writer.flush();
					System.out.println("请求无法识别: " + client.getInetAddress());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
					if (writer != null) {
						writer.close();
					}
					if (client != null) {
						client.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
