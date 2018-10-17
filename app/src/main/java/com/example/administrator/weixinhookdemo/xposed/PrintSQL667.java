package com.example.administrator.weixinhookdemo.xposed;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.example.administrator.weixinhookdemo.WeiXinHookDemo.printCallStack;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PrintSQL667 {
    public static void print(ClassLoader classLoader) throws ClassNotFoundException {

//        Class SQLiteDatabase = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase", classLoader);
//        Class CancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", classLoader);
//        XposedHelpers.findAndHookConstructor(
//                "com.tencent.wcdb.database.SQLiteDirectCursorDriver",
//                classLoader,
//                SQLiteDatabase,
//                String.class,
//                String.class,
//                CancellationSignal,
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        Log.d("SQLiteDirectCursor", "mEditTable=" + param.args[2]);
//                        Log.d("SQLiteDirectCursor", "mSql=" + param.args[1]);
//                    }
//                });

        XposedHelpers.findAndHookMethod("android.database.sqlite.SQLiteDatabase", classLoader, "update",
                String.class, ContentValues.class, String.class, String[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i("SQLiteDirect_update", String.valueOf(param.args[0]));
                        Log.i("SQLiteDirect_update", String.valueOf(param.args[2]));
                    }
                });

        XposedHelpers.findAndHookMethod("android.database.sqlite.SQLiteDatabase", classLoader, "execSQL",
                String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i("SQLiteDirect_execSQL", String.valueOf(param.args[0]));
                    }
                });

        XposedHelpers.findAndHookMethod("android.database.sqlite.SQLiteDatabase", classLoader, "execSQL",
                String.class, Object[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i("SQLiteDirect_execSQL", String.valueOf(param.args[0]));
                    }
                });


        Class CursorFactory = XposedHelpers.findClass("com.tencent.wcdb.database.SQLiteDatabase$CursorFactory", classLoader);
        Class cancellationSignal = XposedHelpers.findClass("com.tencent.wcdb.support.CancellationSignal", classLoader);
        XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", classLoader, "rawQueryWithFactory",
                CursorFactory, String.class, String[].class, String.class, cancellationSignal, new XC_MethodHook() {
                    @SuppressLint("LongLogTag")
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i("SQLiteDirect_rawQueryWithFactory", String.valueOf(param.args[1]));
                        String[] strings = (String[]) param.args[2];
                        if (strings != null && strings.length > 0) {
                            for (String s : strings) {
                                Log.i("SQLiteDirect_rawQueryWithFactory", s);
                            }
                        }
                        Log.i("SQLiteDirect_rawQueryWithFactory", String.valueOf(param.args[3]));

//                        printCallStack("SQLiteDirect_executeSql");

                    }
                });

        XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", classLoader, "executeSql",
                String.class, Object[].class, cancellationSignal, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.w("SQLiteDirect_executeSql", String.valueOf(param.args[0]));
                        Object[] strings = (String[]) param.args[1];
                        if (strings != null && strings.length > 0) {
                            for (Object s : strings) {
                                if (s != null) {
                                    Log.w("SQLiteDirect_executeSql", s.toString() + "(" + PrintHookDemo667.getType(s) + ")");
                                }
                            }
                        }

                        printCallStack("SQLiteDirect_executeSql");

                    }
                });


        String[] CONFLICT_VALUES = new String[]{"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
        XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", classLoader, "insertWithOnConflict",
                String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                    @SuppressLint("LongLogTag")
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        int i2 = 0;

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("INSERT");
                        stringBuilder.append(CONFLICT_VALUES[(int) param.args[3]]);
                        stringBuilder.append(" INTO ");
                        stringBuilder.append(param.args[0]);
                        stringBuilder.append('(');
                        Object[] objArr = null;
                        ContentValues contentValues = (ContentValues) param.args[2];
                        int size = (contentValues == null || contentValues.size() <= 0) ? 0 : contentValues.size();
                        if (size > 0) {
                            Object[] objArr2 = new Object[size];
                            int i3 = 0;
                            for (String str3 : keySet(contentValues)) {
                                stringBuilder.append(i3 > 0 ? "," : "");
                                stringBuilder.append(str3);
                                int i4 = i3 + 1;
                                objArr2[i3] = contentValues.get(str3);
                                i3 = i4;
                            }
                            stringBuilder.append(')');
                            stringBuilder.append(" VALUES (");
                            while (i2 < size) {
                                stringBuilder.append(i2 > 0 ? ",?" : "?");
                                i2++;
                            }
                            objArr = objArr2;
                        } else {
                            stringBuilder.append(param.args[1] + ") VALUES (NULL");
                        }
                        stringBuilder.append(')');
                        Log.d("SQLiteDirect_insertWithOnConflict", stringBuilder.toString());
                        Log.d("SQLiteDirect_insertWithOnConflict", String.valueOf(param.getResult()));

                        if (objArr != null && objArr.length > 0) {
                            for (Object o : objArr) {
                                if (o != null) {
                                    Log.d("SQLiteDirect_insertWithOnConflict", o.toString() + "(" + PrintHookDemo667.getType(o) + ")");

                                }

                            }
                        }

                        printCallStack("SQLiteDirect_insertWithOnConflict");
                    }
                });

        XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", classLoader, "updateWithOnConflict",
                String.class, ContentValues.class, String.class, String[].class, int.class, new XC_MethodHook() {
                    @SuppressLint("LongLogTag")
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ContentValues contentValues = (ContentValues) param.args[1];
                        String[] strArr = (String[]) param.args[3];
                        StringBuilder stringBuilder = new StringBuilder(120);
                        stringBuilder.append("UPDATE ");
                        stringBuilder.append(CONFLICT_VALUES[(int) param.args[4]]);
                        stringBuilder.append(param.args[0]);
                        stringBuilder.append(" SET ");
                        int size = contentValues.size();
                        int length = strArr == null ? size : strArr.length + size;
                        Object[] objArr = new Object[length];
                        int i2 = 0;
                        for (String str3 : keySet(contentValues)) {
                            stringBuilder.append(i2 > 0 ? "," : "");
                            stringBuilder.append(str3);
                            int i3 = i2 + 1;
                            objArr[i2] = contentValues.get(str3);
                            stringBuilder.append("=?");
                            i2 = i3;
                        }
                        if (strArr != null) {
                            for (int i4 = size; i4 < length; i4++) {
                                objArr[i4] = strArr[i4 - size];
                            }
                        }
                        if (!TextUtils.isEmpty((String) param.args[2])) {
                            stringBuilder.append(" WHERE ");
                            stringBuilder.append(param.args[2]);
                        }
                        Log.e("SQLiteDirect_updateWithOnConflict", stringBuilder.toString());
                        Log.d("SQLiteDirect_updateWithOnConflict", String.valueOf(param.getResult()));
                        if (objArr != null && objArr.length > 0) {
                            for (Object o : objArr) {
                                if (o != null) {
                                    Log.e("SQLiteDirect_updateWithOnConflict", o.toString() + "(" + PrintHookDemo667.getType(o) + ")");
                                }
                            }
                        }

                        if ("SnsComment".equalsIgnoreCase((String) param.args[0]) || "SnsInfo".equalsIgnoreCase((String) param.args[0])) {
                            if (objArr != null && objArr.length > 0) {
                            }

                            printCallStack("SQLiteDirect_updateWithOnConflict");
                        }


                        if ("netstat".equalsIgnoreCase((String) param.args[0])) {

                            printCallStack("SQLiteDirect_updateWithOnConflict_netstat");
                        }

                    }
                });

        XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", classLoader, "delete",
                String.class, String.class, String[].class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String param1 = (String) param.args[0];
                        String param2 = (String) param.args[1];
                        String[] param3 = (String[]) param.args[2];
                        String sql = "DELETE FROM " + param1 + (!TextUtils.isEmpty(param2) ? " WHERE " + param2 : "");
                        Log.v("SQLiteDirect_delete", sql);
                        Log.d("SQLiteDirect_delete", String.valueOf((int) param.getResult()));
                        if (param3 != null && param3.length > 0) {
                            for (String s : param3) {
                                Log.v("SQLiteDirect_delete", s);
                            }
                        }

                        if ("SnsComment".equalsIgnoreCase(param1) || "SnsInfo".equalsIgnoreCase(param1)) {

                            printCallStack("SQLiteDirect_delete");
                        }
                    }
                });

    }

    private static Set<String> keySet(ContentValues contentValues) {
        if (Build.VERSION.SDK_INT >= 11) {
            return contentValues.keySet();
        }
        try {
            Field declaredField = Class.forName("android.content.ContentValues").getDeclaredField("mValues");
            declaredField.setAccessible(true);
            return ((HashMap) declaredField.get(contentValues)).keySet();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
