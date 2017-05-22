package Observer;
import Observer.Observer;

public interface Subject {
    public void registerObserver(Observer o);//ע��۲���
    public void removeObserver(Observer o);//ɾ���۲���
    public void notifyObservers(String value); //������״̬�ı�ʱ����������ᱻ���ã���֪ͨ���еĹ۲���
}
