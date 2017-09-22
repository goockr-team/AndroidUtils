package cxx.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class ProcessDialogUtil {

	private static ProcessDialogUtil util = null;

	private static String TAG = "ProcessDialogUtil";

	public static ProgressDialog progressDialog;

	/**
	 * This isUtil.java class ��ȡʵ��
	 * 
	 * @return
	 * @return Util: ��������
	 */
	public static ProcessDialogUtil getUtil() {
		if (util == null) {
			util = new ProcessDialogUtil();
		}
		return util;
	}
	/**
	 * ��ʾһ����Ϣ
	 * 
	 * @param context
	 * @param msg
	 * @return void: ��������
	 */
	public void showText(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʾ��ʾ�� This isUtil.java class
	 * 
	 * @return void: ��������
	 */
	public static void showDialogMSG(Context context) {
		// ��ֹ�ظ�����
		closeDialogMSG();
		progressDialog = new ProgressDialog(context);
		// LogDRC.d(TAG, "====>����showDialogMSG()");
		progressDialog.setCancelable(false);
		progressDialog.setMessage("������...");
		progressDialog.show();
	}

	/**
	 * �ر���ʾ�� This isUtil.java class
	 * 
	 * @return void: ��������
	 */
	public static void closeDialogMSG() {
		// TODO Auto-generated method stub
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
