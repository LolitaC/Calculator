import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Set;
import java.util.Stack;

import org.omg.CORBA.portable.ValueBase;

import Domain.*;
import Observer.Observer;
import Screen.*;

//�۲���
public class Calculator extends Frame implements Observer{

	final private int CAL_WIDTH = 257;//���������
	final private int CAL_HEIGHT = 323;//�������߶�
	private Domain domain;//������
	private Screen screen;//��ʾ��
	
	//������  �����������
	private String space = new String(" ");//���ʽ��ֵ�������֮��ķָ���
	private String editNum = new String();//��ǰ�������ֵ
	private String editNumCache = new String("0");//����ֵ�����������޼���ֵʱ����ֵ�ȼ��ڼ���ֵ
	private String calResult = new String(); //��������ʾ�� ֻ��ʾһ��(����������Ҳ�ø�ֵ����������
	private String exp = new String();//���ʽ�������롰=��ʱ������ñ��ʽ
	private String expCache = new String(); //���ʽ���棬������һ�μ���ı��ʽ�� ��ȥ��һ����ֵ��
	private String op = new String(); //�����
	
	public static void main(String[] args) {
		
		Calculator cal = new Calculator();
		
		
	}
	
	public Calculator() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init() {
		
		//���ڹر��¼�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				 System.exit(0);
			}
		});
		
		domain = new StandardDomain();
		screen = new StandardScreen();
		domain.registerObserver(this);//ע��Ϊ�۲��ߣ���������domain������
		
		BorderLayout border = new BorderLayout();
		this.setLayout(border);//�߽粼��
		this.add("North", screen);
		this.add("Center",  (Component) domain);
		
		this.setSize(CAL_WIDTH,CAL_HEIGHT);//������size
		this.setLocationRelativeTo(null);//���ھ�����ʾ
		setResult(); // ��ʼ�����㴰��
		this.setVisible(true);
	}

	
	@Override
	//��ȡ����(domain)���͵���Ϣ,���������İ�������ļ�ֵ��
	public void update(String value) {
		
		//����
		Calculate(value);
		
		//set the result to the screen interface
		setResult();
	}
	
	//set the result to the screen interface
	private void setResult(){
		
		//��ʾ������
		if (!calResult.isEmpty()) {
			screen.setEditValue(calResult);
			calResult = "";
		}
		//Screen��EditValue��ʾ��ֵ
	    else if(editNum.isEmpty()){//����м���ֵ����ʾ����ֵ��  ������ʾ���뻺������ֵ
			
			screen.setEditValue( editNumCache );
		}
		else{
			screen.setEditValue( editNum );
		}
		
		//Screen��TipValue��ʾ��ֵ
		screen.setTipValue( exp + space + op);
	}
	
	//����
	private void Calculate(String value)
	{
		//�õ������������ֻ�С����
		if(value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3")
				|| value.equals("4") || value.equals("5")  || value.equals("6")  || value.equals("7") 
				|| value.equals("8")  || value.equals("9")  || value.equals(".") )
		{
			_Num(value);
		}
		
		//�õ��������ǡ�ɾ����һ��Ĳ���
		if(value.equals("��")  || value.equals("C")  || value.equals("CE") )
		{
			_Delete(value);
		}
		
		//�õ��������ǡ�+��-��*��/��һ��ı�׼�����
		if(value.equals("+")  || value.equals("-")  || value.equals("*")  || value.equals("/") )
		{
			_StdOP(value);
		}
		
		//�õ��������ǡ�=�����Ա��ʽ��������
		if(value.equals("="))
		{
			_Cal();
		}
		
		//�õ��������ǡ������̡�%��1/x��
		if(value.equals("��") || value.equals("��") || value.equals("%") || value.equals("1/x"))
		{
			_selfOP(value);
		}
	}
	
	//���ݴ�����-�Ի�õ����ݽ��д���
	
	//�õ������������ֻ�С����
	private void _Num(String value) {
		
		if(!editNum.isEmpty())
		{
			
			//��beforeת��Ϊafter�����ݣ�����Ϸ��Ļ���
			//before:  editNum:1     value:2
			//after��   editNum��12
			
			//�����ǰeditNum����ֵΪ0��������������0
			if(editNum.equals("0"))
			{
				if(value.equals("."))
				    editNum = editNum + value;
				else
					editNum = value;
				return;
			}
				
			//�����ǰeditNum�Ѿ���С�����ˣ�������������С����
			if(value.equals(".") && editNum.indexOf(".")!=-1)
			    return;
			
			//�������ϸ������������������Ϸ�����
			editNum = editNum + value ;
				
			
				
		}
		else
		{
			//���ǵ�editNumΪ�յ�ʱ�� �������ݵ����
			
			//�������С���㣬��ôĬ����ǰ�油0
			if(value.equals("."))
				editNum = new String( "0" + value );
			else
			    editNum = new String(value);
		}
	}
	
	//�õ��������ǡ�ɾ����һ��Ĳ���
	private void _Delete(String value)
	{
		//�ԡ� �� C �� CE ���ݵĴ���
		
		//ɾ����ǰ����ֵ�����һ���ַ����൱�ڳ�������
		if(value.equals("��") )
		{
			//�����ǰ����ֵΪ�գ������в���
			if(editNum.isEmpty())
				return ; 
			
			editNum = editNum.substring(0, editNum.length()-1);
			//�������˳��������󣬵�ǰ����ֵΪ�գ����ʼ�����뻺������ֵ
			if(editNum.isEmpty())
				editNumCache = "0";
				
		}
		
		//��յ�ǰ����ֵ
		if(value.equals("CE") )
		{
			editNum = "";
			editNumCache = "0";
		}
		
		//�������
		if(value.equals("C") )
		{
			editNum = "";
			editNumCache = "0";
			op = "";
			exp = "";
			expCache = "";
		}
	}
	
	//�õ���������+��-��*��/�ȱ�׼�����
	private void _StdOP(String value)
	{
		//����������Ϊ�գ������жϱ��ʽ��Ϊ�գ�˵�����ǵ�һ�ε��
		if(!op.isEmpty() )
		{
			//�������������ݣ��򽫸���ֵд����ʽ������ֻ�ı������
			if(!editNum.isEmpty())
			{
				String curValue = new String();
				
				if(editNum.indexOf(".") == -1)//�����Ϊ����
					curValue = editNum;
				else                           //�����Ϊ������
				    curValue = new Double(editNum).toString();
				
				editNumCache = curValue; //д�뻺����
				exp = exp + space + op + space + curValue;//д����ʽ
			}
			
			//�ı������   �ڸú��������һ�䣬��Ϊ����ִ��ʲô��������Ҫ�ı������
		}
		else   //�����Ϊ��
		{
			String curValue = new String();//
			//�������������ݣ���ô��ȡ���������ݣ������ȡ���뻺���������ݣ�editNumCache��
			if(editNum.isEmpty())
			{
				curValue = editNumCache;
				
			}
			else
			{
				if(editNum.indexOf(".") == -1)//�����Ϊ����
					curValue = editNum;
				else                           //�����Ϊ������
				    curValue = new Double(editNum).toString();
				
				
			}
			
			exp = curValue; //���ʽΪ��ֵ
			editNumCache = curValue; //��������ֵĬ��Ϊ��ֵ��
		}
		
		editNum = "";	
		op = value;//�ı������
		
	}
	
	//���ʽ����
	private void _Cal() 
	{
		String curValue;
		if(editNum.isEmpty())
		{
			curValue = editNumCache;
		}
		else
		{
			if(editNum.indexOf(".") == -1)//�����Ϊ����
				curValue = editNum;
			else                           //�����Ϊ������
			    curValue = new Double(editNum).toString();
		}
		
		editNum = "";
		
		String result;
		
		if(exp.isEmpty() && !expCache.isEmpty())  //���ʽΪ�գ����ʽ���治Ϊ0
		{
			//System.out.println(curValue + expCache);
			result = getCalResult(curValue + expCache);
			
		}
		else
		{
			//��������Ϊ��
			if(op.isEmpty())
				exp = exp + curValue;
			else
				exp = exp + space + op + space + curValue ;
			
			op = ""; 
			result = getCalResult(exp);
		}
		
		
		//һ�������������ձ��ʽ�������
		exp = "";
		
		
		if(result != null)//������Ϊ�գ�˵���������
	   	    editNumCache = result;
	}
	
	//���ݴ�����-�Ի�õ����ݽ��д���
	
	
	//���ʽ���㲿��
	
	/**
	 * @description getCalResult �Ա��ʽ������ֵ
	 * @param exp ��Ҫ��������ı��ʽ
	 * @return ������
	 */
	private String getCalResult(String exp)
	{
		String pExp = new String(); //��׺���ʽ
		String result = new String();//������
		
		//�����ʽת��Ϊ��׺���ʽ
		pExp = getPExp(exp);
		
		//�����׺���ʽ
		result = getResult(pExp);
		
		if(result != null)
		{
			//ͨ��������ʽȥ��С�����������0
			if(result.indexOf(".") > 0 && result.indexOf("E") == -1){
		        result = result.replaceAll("0+?$", "");//ȥ�������0
		        result = result.replaceAll("[.]$", "");//�����һλ��.��ȥ��
		    }
		}
		
		//����������
		return result;
	}
	
	/**
	 * @description getPExp ���ñ��ʽת��Ϊ��׺���ʽ
	 * @param exp ��Ҫ����ת���ı��ʽ
	 * @return ת����ĺ�׺���ʽ
	 */
	private String getPExp(String exp)
	{
		String elements[] = exp.split(" ");//exp������ֵ����������� �� ��������
		
		String pExp = new String();//��׺���ʽ
		
		Stack<String> stack = new Stack<String>(); //����һ��ջ����ʱ�洢�����
		
		//�������ʽ
		for (int i = 0; i < elements.length; i++) {
			
			//���˵�һ����ֵ�������Ĵ�����ʽ���棨expCache��
			if(i==0)
				expCache = "";//�����
			else
				expCache = expCache + space + elements[i];
			
			if(elements[i].equals("("))                            //�����Ԫ���ǡ�����
			{
				//ֱ�Ӵ����ջ
				stack.push(elements[i]); 
			}
			else if(elements[i].equals(")"))                           //�����Ԫ���ǡ�����
			{
				String e;
				//��ջ����ջ��Ԫ�ز�Ϊ��������������׺���ʽ��������
				while(!(e = stack.pop()).equals("(") )
				{
					pExp = pExp + space + e;
				}
				
			}
			else if(elements[i].equals("+") || elements[i].equals("-"))   //�����Ԫ���ǡ�+����-��
			{
				//ջ�գ�ֱ�Ӵ���ջ��
				if(stack.isEmpty())
				{
					stack.push(elements[i]);
				}
				else    //ջ���գ�   
				{
					//ջ����
					while(!stack.isEmpty())
					{
						if(!stack.peek().equals("(") )        //ջ��Ԫ�ز�Ϊ������������ջ��Ԫ��д���׺���ʽ��
						    pExp = pExp + space + stack.pop();
						else
							break;
					}
					
					//���÷�����ջ
					stack.push(elements[i]);
				}
				
			}
			else if (elements[i].equals("*") || elements[i].equals("/"))   //�����Ԫ���ǡ�*����/��
			{  
				//ջ�գ�ֱ�Ӵ���ջ��
				if(stack.isEmpty())
				{
					stack.push(elements[i]);
				}
				else    //ջ���գ�   
				{
					//ջ����
					while(!stack.isEmpty())
					{
						if(!stack.peek().equals("(") && !stack.peek().equals("+") && !stack.peek().equals("-"))       //ջ��Ԫ�ز�Ϊ����������+������-��������ջ��Ԫ��д���׺���ʽ��
						    pExp = pExp + space + stack.pop();
						else
							break;
					}
					
					//���÷�����ջ
					stack.push(elements[i]);
				}
				
			}
			else {                                                      //���Ԫ������ֵ
				
				//��׺���ʽ�ĵ�һ��Ԫ�ؿ϶�����ֵ,��һ��Ԫ��ǰ�治��Ҫ�ָ���
				if(pExp.isEmpty())
				{
					pExp = elements[i];
				}
				else
					pExp = pExp + space + elements[i];
			}
			
		}
		
		while (!stack.isEmpty()) {
			pExp = pExp + space + stack.pop();
			
		}
		
		return pExp;
	}
	
	/**
	 * @description getResult ��ȡ��׺���ʽ�ļ�����
	 * @param pExp ��Ҫ���м���ĺ�׺���ʽ
	 * @return ������
	 */
	private String getResult(String pExp)
	{
		String elements[] = pExp.split(" ");  //pExp������ֵ����������� �� ��������
		Stack<String> result = new Stack<String>();    //��Ŵ��������ֵ , ���㵽���ʣ�µ����һ����ֵ���Ǽ�����
		
		String num1; //��һ��������
		String num2; //�ڶ���������
		
		//������׺���ʽ
		for (int i = 0; i < elements.length; i++) {
			
			
			
			//��׺���ʽ���㣬  �ȵ������ǵڶ�����������Ȼ����ǵ�һ��
			if(elements[i].equals("+"))
			{
				num2 = result.pop();
				num1 = result.pop();
				
				//���num1��num2������������ô�����������㣬������и���������
				if(num1.indexOf(".")==-1 && num2.indexOf(".")==-1)
				{
					Integer value = Integer.parseInt(num1) + Integer.parseInt(num2);
					result.push( value.toString() ) ;
				}
				else
				{
					Double value = Double.parseDouble(num1) + Double.parseDouble(num2);
					result.push( value.toString() ) ;
				}
				
			}
			else if (elements[i].equals("-")) {
				
				num2 = result.pop();
				num1 = result.pop();
				
				//���num1��num2������������ô�����������㣬������и���������
				if(num1.indexOf(".")==-1 && num2.indexOf(".")==-1)
				{
					Integer value = Integer.parseInt(num1) - Integer.parseInt(num2);
					result.push( value.toString() ) ;
				}
				else
				{
					Double value = Double.parseDouble(num1) - Double.parseDouble(num2);
					result.push( value.toString() ) ;
				}
			}
            else if (elements[i].equals("*")) {
				
				num2 = result.pop();
				num1 = result.pop();
				
				//���num1��num2������������ô�����������㣬������и���������
				if(num1.indexOf(".")==-1 && num2.indexOf(".")==-1)
				{
					Integer value = Integer.parseInt(num1) * Integer.parseInt(num2);
					result.push( value.toString() ) ;
				}
				else
				{
					Double value = Double.parseDouble(num1) * Double.parseDouble(num2);
					result.push( value.toString() ) ;
				}
			}
            else if (elements[i].equals("/")) {
            	
				num2 = result.pop();
				num1 = result.pop();
				
				
				
				if(Double.parseDouble(num2) == 0)
				{
					calResult = "��������Ϊ0";
					return null;
				}
				else
				{
					Double value = Double.parseDouble(num1) / Double.parseDouble(num2);
					result.push( value.toString() ) ;
				}		
				
            }
            else      //��ֵ
            {
            	result.push(elements[i]);
            }
		}
		
		return result.pop();
	}
	
	//�õ��������ǡ������̡�%��1/x��  �Ե�ǰ������Ӱ��ķ���
	private void _selfOP(String value)
	{
		//���editNumΪ�գ���������editNumCache
		if(editNum.isEmpty())
		{
			//�ı�������
			if(value.equals("��"))
			{
				if(editNumCache.startsWith("-"))  
				{
					editNumCache = editNumCache.substring(1);
				}
				else
					editNumCache = "-" + editNumCache;
			}
			else if(value.equals("��"))
			{
				Double data = Math.sqrt(Double.parseDouble(editNumCache));
				editNumCache = data.toString();
			}
			else if (value.equals("%")) 
			{
				Double data = Double.parseDouble(editNumCache) * 0.01;
				editNumCache = data.toString();
			}
			else if (value.equals("1/x")) 
			{
				Double data = 1 / Double.parseDouble(editNumCache) ;
				editNumCache = data.toString();
			}
		}
		else      //�����������
		{
			//�ı�������
			if(value.equals("��"))
			{
				if(editNum.startsWith("-"))  
				{
					editNum = editNum.substring(1);
				}
				else
					editNum = "-" + editNum;
			}
			else if(value.equals("��"))
			{
				Double data = Math.sqrt(Double.parseDouble(editNum));
				editNum = data.toString();
			}
			else if (value.equals("%")) 
			{
				Double data = Double.parseDouble(editNum) * 0.01;
				editNum = data.toString();
			}
			else if (value.equals("1/x")) 
			{
				Double data = 1 / Double.parseDouble(editNum) ;
				editNum = data.toString();
			}
		}
	}
	
	//���ʽ���㲿��
	
}
