package sample;

import com.abee.ftp.common.state.ResponseBody;
import com.abee.ftp.client.MyFtpClient;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    MyFtpClient client = new MyFtpClient();

    public Button button_connect;

    public Button button_download;

    public Button button_upload;

    public Button button_confirm;

    public TextField remote;

    public TextField local;

    public TextField catalog;

    public TextArea show_list;

    public TextArea show_response;

    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void Print_List(String x)
    {
        String y,z;
        int m,p,q;
        m=x.length();
        y=x.substring(1,m-1);
        p=y.indexOf("=");
        if(p!=-1) {
            z = y.substring(0, p);
            show_list.appendText(z);
            show_list.appendText("\n");
            int r = z.length();
            y = y.substring(r+1,y.length());
        }
        for(m=0;m<y.length();m++)
        {
            p=y.indexOf(" ");
            q=y.indexOf("=");
            if(p!=-1&&q!=-1) {
                z = y.substring(p+1, q);
                show_list.appendText(z);
                show_list.appendText("\n");
                y=y.substring(q+1,y.length());
            }
            if(p==-1||q==-1)
                break;
        }
    }
    public void Button_Connect(javafx.event.ActionEvent actionEvent)throws IOException,ClassNotFoundException {
        client.connect("localhost", 2221);
        String init="D:/server";

        ResponseBody a = client.switch1(init);//切换目录
        ResponseBody b = client.switch2(init);//输出当前目录
        Map<String, Boolean> c = client.switch3(init);//打印列表

        Print_List(c.toString());

        show_response.appendText(a.toString());
        show_response.appendText("\n");
        show_response.appendText(b.toString());
        show_response.appendText("\n");

    }

    public void Button_Confirm(javafx.event.ActionEvent actionEvent)throws IOException,ClassNotFoundException {
        String catalog_address=catalog.getText().toString();

        ResponseBody a = client.switch1(catalog_address);  //切换目录
        ResponseBody b = client.switch2(catalog_address);  //输出当前目录
        Map<String, Boolean> c = client.switch3(catalog_address);  //打印列表
        show_list.setText("");

        Print_List(c.toString());

        show_response.appendText(a.toString());
        show_response.appendText("\n");
        show_response.appendText(b.toString());
        show_response.appendText("\n");

    }

    public void Button_Download(javafx.event.ActionEvent actionEvent)throws ClassNotFoundException,IOException{
        String local_address=local.getText().toString();
        String remote_address=remote.getText().toString();
        client.download(remote_address,local_address);
    }

    public void Button_Upload(javafx.event.ActionEvent actionEvent)throws ClassNotFoundException,IOException {
        String local_address=local.getText().toString();
        String remote_address=remote.getText().toString();
        client.upload(local_address,remote_address);
    }
}

