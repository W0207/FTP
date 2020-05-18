package sample;

import com.abee.ftp.client.secure.Authenticator;
import com.abee.ftp.common.state.ResponseBody;
import com.abee.ftp.client.MyFtpClient;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author shibin zhan
 */
public class Controller implements Initializable{

    MyFtpClient client = new MyFtpClient();

    Authenticator authenticator = new Authenticator();

    public Button button_connect;

    public Button button_download;

    public Button button_upload;

    public Button button_confirm;

    public TextField remote;

    public TextField ip;

    public TextField lport;

    public TextField sport;

    public TextField local;

    public TextField address;

    public TextArea show_response;

    public TextArea show_dictionary;

    public CheckBox encryption;

    public String init;

    boolean secure = false;

    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void Print_List(String x){
        show_dictionary.clear();
        String y,z;
        int m,p,q;
        m=x.length();
        y=x.substring(1,m-1);
        p=y.indexOf("=");
        if(p!=-1) {
            z = y.substring(0, p);
            show_dictionary.appendText(z);
            show_dictionary.appendText("\n");
            int r = z.length();
            y = y.substring(r+1);
        }
        for(m=0;m<y.length();m++)
        {
            p=y.indexOf(" ");
            q=y.indexOf("=");
            if(p!=-1&&q!=-1) {
                z = y.substring(p+1, q);
                show_dictionary.appendText(z);
                show_dictionary.appendText("\n");
                y=y.substring(q+1);
            }
            if(p==-1||q==-1)
                break;
        }
    }

    public void print(String root) throws IOException, ClassNotFoundException {
        ResponseBody a = client.switch1(root);//切换目录
        ResponseBody b = client.switch2(root);//输出当前目录
        Map<String, Boolean> c = client.switch3(root);//打印列表
        Print_List(c.toString());
        show_response.appendText(a.toString());
        show_response.appendText("\n");
        show_response.appendText(b.toString());
        show_response.appendText("\n");
    }

    public void Button_Confirm(javafx.event.ActionEvent actionEvent)throws IOException,ClassNotFoundException {
        show_dictionary.clear();
        String new_address=address.getText();
        print(new_address);
    }

    public void Button_Connect(javafx.event.ActionEvent actionEvent)throws IOException,ClassNotFoundException {
        String ip_address=ip.getText();
        int port1=Integer.parseInt(lport.getText());
        int port2=Integer.parseInt(sport.getText());
        client.connect(ip_address, port1);
        authenticator.connect(ip_address, port2);
        authenticator.authenticate();
        init="D:/1232112";
        ResponseBody a = client.switch1(init);//切换目录
        ResponseBody b = client.switch2(init);//输出当前目录
        int c=b.toString().length();
        init=b.toString().substring(28,c-2);
        address.clear();
        address.appendText(init);
        print(init);
    }

    public void Button_Download(javafx.event.ActionEvent actionEvent)throws ClassNotFoundException,IOException{
        if(encryption.isSelected())
            secure=true;
        String local_address=local.getText();
        String remote_address=remote.getText();
        client.download(remote_address,local_address,secure);
    }

    public void Button_Upload(javafx.event.ActionEvent actionEvent)throws ClassNotFoundException,IOException {
        if(encryption.isSelected())
            secure=true;
        String local_address=local.getText();
        String remote_address=remote.getText();
        client.upload(local_address,remote_address,secure);
    }
}

