package Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.PopupMenu;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class StandardScreen extends Screen{
	
	private final int EDIT_HEIGHT = 30; //"edit"��ǩ�ĸ߶�
	private final int TIP_HEIGHT = 20;  //"tip"��ǩ�ĸ߶�
	private final int EDIT_FONT_SIZE = 24; //"edit"��ǩ�����С
	private final int EDIT_FONT_SIZE_SMALL = 18; //���ȹ���ʱ��ŪСһ������
	private final int TIP_FONT_SIZE = 12;//"tip"��ǩ�����С
	
	private final int editChangeSizeLength = 18; //���editValue����18λ���������С
	
	//private final int MARGIN = 1; //margin
	JLabel tipLabel = new JLabel("",SwingConstants.RIGHT);//
	JLabel editLabel = new JLabel("",SwingConstants.RIGHT);//
	
	public StandardScreen(){
		
		init();
	}
	
	private void init()
	{
		
		
		//JLabelĬ�ϵĴ�С�Ǹ����ı����Ⱥʹ�С��������
		//ʹ��setMaximumSize��setMinimumSize����������С����С��С��Ч
		//����ͨ�����ñ�ǩ����Ѵ�СsetPreferredSize������ʵ��
		tipLabel.setPreferredSize(new Dimension(getWidth(), TIP_HEIGHT));
		editLabel.setPreferredSize(new Dimension(getWidth(), EDIT_HEIGHT));
	
		//���������С
		tipLabel.setFont(new Font("Serif", Font.PLAIN, TIP_FONT_SIZE));
		editLabel.setFont(new Font("Serif", Font.BOLD, EDIT_FONT_SIZE));
		
		BorderLayout border	= new BorderLayout();//�߽粼��
		//border.setHgap(MARGIN);//margin����
		//border.setVgap(MARGIN);//margin����
		this.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)));//���ñ߿���ɫ����ʽ
		this.setLayout(border);//���ò���
		this.setBackground(new Color(238, 238, 238));//����ɫ

		//����margin
		//this.add(new Panel(),"North");
		//this.add(new Panel(),"South");
		this.add(new Panel(),"East");
		this.add(new Panel(),"West");
		
		//����content
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBackground(new Color(238, 238, 238));
		content.add(tipLabel, "North");
		content.add(editLabel , "Center");
		this.add(content,"Center");
		
		
	}

	@Override
	public String getTipValue() {
		// TODO Auto-generated method stub
		return tipLabel.getText();
	}

	@Override
	public Boolean setTipValue(String value) {
		// TODO Auto-generated method stub
		tipLabel.setText(value);
		return Boolean.TRUE;
	}

	@Override
	public String getEditValue() {
		// TODO Auto-generated method stub
		return editLabel.getText();
	}

	@Override
	public Boolean setEditValue(String value) {
		
		/*
		//ͨ��������ʽȥ��С�����������0
		if(value.indexOf(".") > 0){
	        value = value.replaceAll("0+?$", "");//ȥ�������0
	        value = value.replaceAll("[.]$", "");//�����һλ��.��ȥ��
	    }
	    */
		
		// TODO Auto-generated method stub
		if(value.length() > editChangeSizeLength)
			editLabel.setFont(new Font("Serif", Font.BOLD, EDIT_FONT_SIZE_SMALL));
		else
			editLabel.setFont(new Font("Serif", Font.BOLD, EDIT_FONT_SIZE));
			
		editLabel.setText(value);
		return Boolean.TRUE;
	}
}
