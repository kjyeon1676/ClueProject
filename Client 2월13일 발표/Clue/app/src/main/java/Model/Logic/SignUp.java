package Model.Logic;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hyun on 2015-01-27.
 */
public class SignUp {

    public String signUp(String id, String password,String gender,String email )
    {
        return "1$"+id+"$"+password+"$"+gender+"$"+id+"$"+email;
    }
    public void idRepeatError(Context context)
    {
        Toast.makeText(context, "아이디가 존재합니다", Toast.LENGTH_LONG).show();
    }

    public void passwordError(Context context)
    {
        Toast.makeText(context, "비밀번호가 5자 이하입니다.",Toast.LENGTH_LONG).show();
    }

    public void idRepeatSuccess(Context context)
    {
        Toast.makeText(context, "사용 가능합니다.",Toast.LENGTH_LONG).show();
    }

    public void repeatCheck(Context context)
    {
        Toast.makeText(context, "중복확인을 눌러주세요.",Toast.LENGTH_LONG).show();
    }
    public void signUpSuccess(Context context)
    {
        Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
    }
    public void passwordError2(Context context)
    {
        Toast.makeText(context,"#,$,%,& 이 포함된 비밀번호는 사용하실 수 없습니다.",Toast.LENGTH_LONG).show();
    }
    public void passwordError3(Context context)
    {
        Toast.makeText(context,"비밀번호를 다시 확인해주세요!!.",Toast.LENGTH_LONG).show();
    }
    public void genderCheck(Context context)
    {
        Toast.makeText(context,"성별을 체크해주세요!!",Toast.LENGTH_LONG).show();

    }
}

