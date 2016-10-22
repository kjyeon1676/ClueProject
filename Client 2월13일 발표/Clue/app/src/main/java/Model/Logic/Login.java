package Model.Logic;

import android.content.Context;
import android.widget.Toast;

import Model.Data.User;

/**
 * Created by hyun on 2015-01-27.
 */
public class Login {

    public String login(String id, String password )
    {
        return "2$"+id+"$"+password;
    }

    public void loginError(Context context)
    {
       Toast.makeText(context, "아이디나 비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
    }


}
