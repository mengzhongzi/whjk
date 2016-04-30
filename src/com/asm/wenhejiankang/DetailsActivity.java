package com.asm.wenhejiankang;
import android.widget.TextView;
import com.asm.wenhejiankang.net.NetContext;
import com.asm.wenhejiankang.model.User;
import com.asm.wenhejiankang.net.Net_whjk;
/*
个人信息界面
风的影子
*/
public class DetailsActivity extends StartActivity
	{
		TextView info_accounts,info_name,info_marital_status,info_gender,info_doctor,info_identity_card_number,info_address,
		info_tel,info_mail,info_previous_history,info_allergy,info_contagion,info_personal_life;
 XlApplication application;
 User user;
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.info);
				//帐号
				info_accounts = (TextView)findViewById(R.id.info_accounts);
				//姓名
				info_name = (TextView)findViewById(R.id.info_name);
				//婚姻状况
				info_marital_status = (TextView)findViewById(R.id.info_marital_status);
				//性别
				info_gender = (TextView)findViewById(R.id.info_gender);
				//责任医生
				info_doctor = (TextView)findViewById(R.id.info_doctor);
				//身份证号
				info_identity_card_number = (TextView)findViewById(R.id.info_identity_card_number);
				//地址
				info_address = (TextView)findViewById(R.id.info_address);
				
				info_tel = (TextView)findViewById(R.id.info_tel);
				
				info_mail = (TextView)findViewById(R.id.info_mail);
				
				info_personal_life = (TextView)findViewById(R.id.info_personal_life);
				
				info_allergy = (TextView)findViewById(R.id.info_allergy);
				info_contagion = (TextView)findViewById(R.id.info_contagion);
				info_personal_life=(TextView)findViewById(R.id.info_personal_life);
				application = (XlApplication)getApplication();
				setInfoText();
			}
	  
		//配置数据
		void setInfoText()
		{
			Net_whjk net = application.getNetContext();
			User user = application.getUser();
			info_accounts.setText("");
			if(user!=null)
			{
			info_name.setText(	user.getName());	
			info_marital_status.setText(user.getMaritalStatus());
			info_gender.setText(user.getGender());
			info_doctor.setText(user.getDortor());
			info_identity_card_number.setText(user.getIdentityNumber());
			info_address.setText(user.getAddress());
			info_tel.setText(user.getTel());
			info_mail.setText(user.getEmail());
			}
			}
	
	
	
	
	
}
