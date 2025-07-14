package com.mall.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

public class RandomCodeServlet extends HttpServlet {

	//验证码宽度
	private int width = 60;

	//验证码高度
	private int height = 20;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		//创建一个随机数生成器
		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//创建字体，字体的大小应该根据图片的高度来定
		Font font = new Font("Times New Roman", Font.PLAIN, 18);
		g.setFont(font);
		//画边框
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生160条干扰线,使图像中的认证码不易被其他程序探测到
		g.setColor(Color.GRAY);
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x1, y1);
		}
		//randomCode用于保存随机产生的验证码，以便于用户登录后进行验证
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		//
		for (int i = 0; i < 4; i++) {
			//  随机产生4位数字的验证码
			String strRand = String.valueOf(random.nextInt(10));
			//产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
			red = random.nextInt(110);
			green = random.nextInt(50);
			blue = random.nextInt(50);
			//用随机产生的颜色将验证码绘制到图像中
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, 13 * i + 6, 16);
			//将产生的四个随机数组合在一起
			randomCode.append(strRand);
		}
		//将四位数字的验证码保存到Session中
		HttpSession session = req.getSession();
		session.setAttribute("randomCode", randomCode.toString());
		//禁止图像缓存
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		//将图像输出到Servlet输出流中
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

}
