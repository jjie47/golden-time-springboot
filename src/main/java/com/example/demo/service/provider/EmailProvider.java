package com.example.demo.service.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {
	
	private final JavaMailSender javaMailSender;
	
	private final String SUBJECT = "[골든타임] 인증 번호 안내드립니다.";
	
	// 인증번호 메일 전송
	public boolean sendCertificationMail(String email, String certificationNumber) {
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);	
			
			String htmlContent = getCertificationMessage(certificationNumber);
			
			messageHelper.setTo(email);
			messageHelper.setSubject(SUBJECT);
			messageHelper.setText(htmlContent, true);
			
			javaMailSender.send(message);
			
		} catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	
	// 이메일 메시지 내용
	private String getCertificationMessage (String certificationNumber) {
		String certificationMessage = "";
		certificationMessage += "<table style='margin: 40px auto 20px; text-align: left; border-collapse: collapse; border: 0px; width: 600px; padding: 64px 16px; box-sizing: border-box;'>\r\n"
				+ "    <tbody>\r\n"
				+ "        <tr>\r\n"
				+ "            <td style=''>\r\n"
				+ "                <a href='#' target='_blank' style='display: inline-block;'>\r\n"
				+ "                    <img src='https://yeelight.speedgabia.com/11S/logo.png'>\r\n"
				+ "                </a>\r\n"
				+ "            <p style='padding-top: 48px; font-weight: 700; font-size: 20px; line-height: 1.5; color: #222'>\r\n"
				+ "                이메일 주소를 인증해주세요.\r\n"
				+ "            </p>\r\n"
				+ "            <p style='font-size: 16px; font-weight: 400; line-height: 1.5; padding-top: 16px'>\r\n"
				+ "                진행하시던 화면으로 돌아가 아래 인증번호를 입력하시면 인증이 완료됩니다.\r\n"
				+ "            </p>\r\n"
				+ "            <div style=' margin-top: 48px; font-size: 16px; font-weight: 400; line-height: 1.5; border-radius: 8px; background: #e1e9ea; color: #595959; padding: 16px 20px;'>\r\n"
				+ "                인증번호<br>\r\n"
				+ "                <b style='font-size: 24px; color: #222; font-weight: 700; letter-spacing: 1.92px'>"
				+ certificationNumber
				+ "				   </b>\r\n"
				+ "            </div>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td style='padding-top: 48px' colspan='2'>\r\n"
				+ "                <div style='padding: 24px; background: #f2f2f2; border-radius: 8px'>\r\n"
				+ "                    <span style='font-size: 14px; font-weight: 400; line-height: 1.5; color: #222'>\r\n"
				+ "                    본 메일은 발신전용으로 회신되지 않습니다.\r\n"
				+ "                    </span>\r\n"
				+ "                </div>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "    </tbody>\r\n"
				+ "    <tfoot>\r\n"
				+ "        <tr>\r\n"
				+ "            <td style='padding-top: 48px'>\r\n"
				+ "                <hr style='margin: 8px 0; border-color: #d9d9d9; border-style: solid; border-width: 1px 0 0 0'>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td style='padding-top: 8px; font-size: 12px; font-style: normal; font-weight: 400; line-height: 1.5; color: #222;'>\r\n"
				+ "            <p style='font-size: 14px; font-weight: 700'>GOLDENTIME</p>\r\n"
				+ "            <p>골든타임</p>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "    </tfoot>\r\n"
				+ "</table>\r\n";
		
		return certificationMessage;
	}
	
}
 