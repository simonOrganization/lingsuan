package com.ling.suandashi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ling.suandashi.R;
import com.ling.suandashi.base.BasicActivity;
import com.ling.suandashi.fragment.FgHome;
import com.ling.suandashi.fragment.FgMiddle;
import com.ling.suandashi.fragment.FgMy;
import com.ling.suandashi.tools.CommonUtils;
import com.ling.suandashi.view.NoScrollViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgreementActivity extends BasicActivity{

    @BindView(R.id.base_title_title_tv)
    TextView title;
    @BindView(R.id.agreement_tv)
    TextView info;

    private String agreement = "\n灵算大师版权声明\n" +
            "\n" +
            "灵算大师充分尊重和保护权利人的合法权利，灵算大师根据我国相关法律法规的规定，特制定本声明。对于软件中用户上传的作品，用户已承诺拥有完整的著作权或已获得了著作权人的相应授权，其上传行为不侵犯他人著作权或其他权益。如果您认为灵算大师提供的服务内容或用户上传的作品侵犯了您的合法权利，您有权向灵算大师发出权利通知，我们将根据中国法律法规和政府规范性文件采取措施，包括但不限于通知内容提供方、删除相关内容或断开、屏蔽相关链接等。\n" +
            "为了及时有效处理您的投诉，请您详细阅读本声明并依照要求提交权利通知书。 权利人应向灵算大师提交书面权利通知书，通知书的内容应包括但不限于以下内容：\n" +
            "\n" +
            "1.权利人主体信息和相关材料，包括权利人的姓名（名称）、联系方式、地址及营业执照（单位）、身份证（个人）、相关授权证明等证明权利人主体资格的材料；\n" +
            "\n" +
            "2.权利人需提供涉嫌侵权作品在信息网络上的位置、该作品的准确名称以及作者。\n" +
            "\n" +
            "3.构成侵权的初步证明材料。该初步证明材料应包括：\n" +
            "（1）权利人拥有权利的权属证明材料，包括但不限于相关有权机构颁发的证书、作品首次公开发表或发行日期证明材料、创作手稿等能证明权利人拥有相关权利的有效权属证明；\n" +
            "（2）如为授权取得，权利人还需提供完整的授权文件。（3）被投诉内容构成侵权的证明：包括但不限于被投诉方内容构成对权利人的商标、名誉等合法权益侵犯的有效证明等。\n" +
            "\n" +
            "4.权利人应保证权利人在通知书中的陈述和提供的相关材料皆是真实、有效和合法的，并保证承担由此产生的全部法律责任，比如因灵算大师根据权利人的通知书而删除或者断开有关侵权服务的链接或相关内容而给灵算大师造成的任何损失，包括但不限于灵算大师因向被投诉方或用户赔偿而产生的等损失以及灵算大师名誉、商誉损害等。\n" +
            "本声明中的权利人，指拥有合法权益的原始所有人或经原始所有人合法授权的代理人，包括自然人、法人或其他组织等。权利通知书应当经过权利人签署，如您是依法成立的法人或其他组织，请您在权利通知书上加盖公章。\n" +
            "为了确保投诉的真实性和有效性，权利人的书面通知书及其他相关证明材料，原则上应提供原件，不能提供原件的，应提供复印件（在复印件上应有权利人的签章），若材料涉外的，应按照法律的规定进行公证转递，并同时提供相应的公证转递材料。\n" +
            "若权利人已经因为被投诉内容向相关政府部门或法院提起行政投诉或诉讼的，请在提交通知书时，将相关受理证明及提交政府部门或法院的证据材料一同提交给灵算大师，这将有利于权利人的投诉的处理。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        title.setText("用户协议");
        info.setText(agreement);
    }

    @OnClick(R.id.base_title_back)
    public void onClick(View view){
        Toast.makeText(this,"点击返回",Toast.LENGTH_SHORT).show();
    }


}
