package Domain;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Observer.Observer;

public class StandardDomain extends Domain implements ActionListener{
	
	final private int MARGIN = 1;//�ؼ�֮��ľ���
	
	public StandardDomain() {
		// TODO Auto-generated constructor stub
		
		init();
	}
	
	private void init() {
		
		//this.setMaximumSize(new Dimension(10, 50));
		//this.setMinimumSize(new Dimension(10, 50));
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
		//���������
		setLayout(gridbag);
		
		c.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight = 1;
        c.gridwidth = 1;
		
		//��һ��
        c.gridy = 0;
        makebutton("��", gridbag, c);
        makebutton("CE", gridbag, c);
        makebutton("C", gridbag, c);
        makebutton("��", gridbag, c);
        makebutton("��", gridbag, c);
        //��һ��
        
        //�ڶ���
        c.gridy = 1;
        makebutton("7", gridbag, c);
        makebutton("8", gridbag, c);
        makebutton("9", gridbag, c);
        makebutton("/", gridbag, c);
        makebutton("%", gridbag, c);
        //�ڶ���

        //������  
        c.gridy = 2;
        makebutton("4", gridbag, c);
        makebutton("5", gridbag, c);
        makebutton("6", gridbag, c);
        makebutton("*", gridbag, c);
        makebutton("1/x", gridbag, c);
        //������
        
        //������
        c.gridy = 3;
        makebutton("1", gridbag, c);
        makebutton("2", gridbag, c);
        makebutton("3", gridbag, c);
        makebutton("-", gridbag, c);
        c.gridheight = 2 ;//������
        makebutton("=", gridbag, c);
        //������
        
        //������
        c.gridy = 4;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        makebutton("0", gridbag, c);
        
        c.gridx = 2;
        c.gridwidth = 1;
        makebutton(".", gridbag, c);
        
        c.gridx = 3;
        makebutton("+", gridbag, c);
        //������

        
	}
	
	protected void makebutton(String name,
                              GridBagLayout gridbag,
                              GridBagConstraints c) {
        JButton button = new JButton(name); 
        
        //��������
        button.setFont(new Font("����", Font.BOLD, 10));
        
        //ȥ����ť������Χ�Ľ����
        button.setFocusPainted(false);
        
        //�¼�����
        button.addActionListener(this);
        
        //���ô˲�����ָ�������Լ��������
        //��button�����Լ����������Ϊc
        gridbag.setConstraints(button, c);
        add(button);
    }

	
	@Override
	//����ť���ʱ��ִ�д˷��������۲��߷���֪ͨ
	public void notifyObservers(String value) {
		// TODO Auto-generated method stub
		for(int i = 0; i < observers.size(); i++)
		{
			Observer observer = (Observer)observers.get(i);
			observer.update(value);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		notifyObservers(btn.getText());
	}
	
}
