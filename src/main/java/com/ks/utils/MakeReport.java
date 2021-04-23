package com.ks.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.ks.model.User;

import jp.co.nobworks.openfunxion4.core.BlockLayout;
import jp.co.nobworks.openfunxion4.core.Box;
import jp.co.nobworks.openfunxion4.core.Line;
import jp.co.nobworks.openfunxion4.core.OpenFunXion;
import jp.co.nobworks.openfunxion4.core.OpenFunXionException;
import jp.co.nobworks.openfunxion4.core.Text;

public class MakeReport {
	final private static String XML_FILE = "C:/Users/nguye/eclipse-workspace/kenshuu/report.xml";
	final private static String PDF_FILE = "C:/Users/nguye/eclipse-workspace/kenshuu/report.pdf";;

	public void exec(List<User> userList) {
		OpenFunXion ofx = new OpenFunXion(XML_FILE); // 帳票情報XMLファイルの読み込み
		try {
			ofx.setDebug(true); // デバッグモードの指定
			ofx.open(PDF_FILE); // 出力PDFファイルのオープン
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (OpenFunXionException e) {
			e.printStackTrace();
			return;
		}
		makePdf(ofx, userList); // PDFファイルへの出力処理
	}

	public void makePdf(OpenFunXion ofx, List<User> dataList) {

		Collections.sort(dataList, new Comparator<User>() {//dataList配列をAuthorityId（役職）で並び
			@Override
			public int compare(User z1, User z2) {
				if (z1.getAuthorityId() > z2.getAuthorityId())
					return 1;
				if (z1.getAuthorityId() < z2.getAuthorityId())
					return -1;
				return 0;
			}
		});
		HashSet<Integer> roleSize = new HashSet<Integer>();//dataList配列のAuthorityId数を保存する
		for (int i = 0; i < dataList.size(); i++) {
			roleSize.add(dataList.get(i).getAuthorityId());
		}
		int id = dataList.get(0).getAuthorityId();//最初のUserのAuthorityId

		// Y方向の移動量を決める
		// １行１２ドットでデザインしたので、3行分移動
		int moveY = 12 * 3;

		int pageNo = 1;
		int pageSum = sumPage(dataList)+roleSize.size();//総ページ数
		// レイアウトの固定部を出力
		printOutline(ofx, dataList.get(0).getRole().getAuthorityName() == "" ? " 未登録"
				: dataList.get(0).getRole().getAuthorityName());
		// ページ部を取得
		Text page = ofx.getText("page");
		// 初期ページ数の設定
		page.setMessage("PAGE:" + pageNo + "/" + pageSum);

		// ページ部の出力
		page.print();

		// 改ページ用のカウンタ
		int count = 0;

		// デザインツールで作成した各一覧項目の取得
		Line rowLine = ofx.getLine("row_line");
		Text index = ofx.getText("index");
		Text name = ofx.getText("name");
		Text userId = ofx.getText("user_id");
		Text gender = ofx.getText("gender");
		Text age = ofx.getText("age");

		// 上記は それぞれの型にあわせたメソッドで取得していますが
		// これはキャストして取得する例
		Box reverseRow = (Box) ofx.getPrintObject("reverse_row");
		BlockLayout dataBlock = ofx.getBlockLayout("data_block");
		int j = 1;//エレメントのCOUNT
		for (int i = 0; i < dataList.size(); i++) {

			User model = dataList.get(i);

			if (count > 0 && count % 15 == 0 || model.getAuthorityId() != id) {//15行や違う役職の場合、改ページ
				// 改ページ
				ofx.newPage();
				// 改ページしたので、位置を元に戻す
				// それぞれのオブジェクトでやってもいいが、
				// BlockLayout で指定すると簡単
				dataBlock.resetPosition();
				// 新しいページの固定部を出力
				printOutline(ofx, dataList.get(i).getRole().getAuthorityName() == "" ? " 未登録"
						: dataList.get(i).getRole().getAuthorityName());
				pageNo++;
				page.setMessage("PAGE:" + pageNo + "/" + pageSum);
				page.print();
				id = model.getAuthorityId();
				j = 1;
			}
			index.setMessage(String.valueOf(j));
			index.print();
			index.moveY(moveY);

			name.setMessage(model.getFamilyName() + " " + model.getFirstName());
			name.print();
			name.moveY(moveY);

			userId.setMessage(model.getUserId());
			userId.print();
			userId.moveY(moveY);

			if (model.getGender().getGenderName() == "") {
				gender.setMessage("未入力");//GenderIDが未入力

			} else {
				gender.setMessage(String.valueOf(model.getGender().getGenderName()));

			}
			gender.print();
			gender.moveY(moveY);

			if (model.getAge() == 0) {
				age.setMessage("未入力");//Ageが未入力
			} else {
				age.setMessage(String.valueOf(model.getAge()));
			}

			age.print();
			age.moveY(moveY);

			rowLine.print();
			rowLine.moveY(moveY);

			count++;
			j++;

		}

		// 終了処理
		ofx.out();
	}

	/**
	 * 外観部分の出力
	 */
	public void printOutline(OpenFunXion ofx, String authorityName) {
		//        ofx.print( "body_block" );
		// または
		ofx.print("text_1");
		ofx.print("text_2");
		ofx.print("text_14");
		ofx.print("header_box");
		ofx.print("header_1");
		ofx.print("header_2");
		ofx.print("header_3");
		ofx.print("header_4");
		ofx.print("header_5");
		ofx.print("out_box");
		Text time = ofx.getText("create_date");
		String timeStamp = new SimpleDateFormat("yyyy/M/dd HH:mm:ss").format(new Date());
		time.setMessage(timeStamp);//create_time を書く
		time.print();
		Text authority_name = ofx.getText("authority_name");
		authority_name.setMessage(authorityName);//役職を書く
		authority_name.print();

	}

	public int sumPage(List<User> list) {
		int sum = 0;
		int size = list.size();
		int idStart = list.get(0).getAuthorityId();
		int count = 0;
		int max = 0;
		for (int i = 0; i < size; i++) {
			int id = list.get(i).getAuthorityId();
			if (id == idStart) {
				count++;

			}
			if (count >= 15) {
				sum = count / 15;//それぞれに役職のページ数（完全にエレメントが記入しておく）
			}
			if (id != idStart) {
				idStart = list.get(i).getAuthorityId();
				count = 0;
				max += sum;//総ページ数
			}

		}
		return max;
	}

}
