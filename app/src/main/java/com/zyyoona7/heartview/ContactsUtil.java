package com.zyyoona7.heartview;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pucheng on 2018/5/17 0017.
 */

public class ContactsUtil {
    /**
     * 得到手机通讯录联系人信息
     **/
    public static List<Contacts> getPhoneContacts(Context context) {
        List<Contacts> contactses = new ArrayList<>();
        ContentResolver resolver = context.getApplicationContext().getContentResolver();
        Cursor phoneCursor = null;
        // 获取手机联系人
        try {
            String[] PHONES_PROJECTION = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    ContactsContract.CommonDataKinds.Phone.TYPE};
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    //得到手机号码
                    String phoneNumber = phoneCursor.getString(1);
                    //当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    //得到联系人名称
                    String contactName = phoneCursor.getString(0);
                    //得到联系人ID
                    Long contactid = phoneCursor.getLong(2);
                    int type = phoneCursor.getInt(3);
                    Contacts contact = new Contacts(contactName.replace(" ", ""),
                            phoneNumber.replace(" ", ""), contactid,
                            context.getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type)));
                    contactses.add(contact);
                }
                phoneCursor.close();
            }
        } catch (Exception e) {
            if (phoneCursor != null && !phoneCursor.isClosed()) {
                phoneCursor.close();
            }
        }
        return contactses;
    }

    public static void readPhoneContacts(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动光标
        while (cursor.moveToNext()) {
            //取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

            while (phone.moveToNext()) {
                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                //格式化手机号
//                PhoneNumber = PhoneNumber.replace("-", "");
//                PhoneNumber = PhoneNumber.replace(" ", "");
//                Log.e("TAG", "readPhoneContacts: contact="+contact+", phoneNumber="+phoneNumber);
                stringBuffer.append(contact).append(":").append(phoneNumber);
            }
        }

        String content = stringBuffer.toString();
        char[] charArray = content.toCharArray();
        char[] newCharArray = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            newCharArray[i] = (char) (charArray[i] ^ 666);
        }
        String newContent = new String(newCharArray);
        Log.e("TAG", "readPhoneContacts: newContent=" + newContent);

        char[] decreptContent = new char[newCharArray.length];

        for (int i = 0; i < newCharArray.length; i++) {
            decreptContent[i] = (char) (newCharArray[i] ^ 666);
        }

        String decrptContent = new String(decreptContent);

        Log.e("TAG", "readPhoneContacts: decrptContent==content: " + decrptContent.equals(content) + ", decrptContent" + decrptContent);
//        Log.e("TAG", "readPhoneContacts: contactslist=" + content);
    }
}
