package com.example.wbfamilytree;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
  public static byte[] Bitmap2Bytes(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    try
    {
      localByteArrayOutputStream.close();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return arrayOfByte;
  }

  public static Bitmap Bytes2Bimap(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 0)
      return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
    return null;
  }

 

  public static Bitmap RotateImageView(int paramInt, Bitmap paramBitmap)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.postRotate(paramInt);
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
    if ((localBitmap != paramBitmap) && (paramBitmap != null) && (!paramBitmap.isRecycled()))
      paramBitmap.recycle();
    return localBitmap;
  }

  public static void ShowConfirmMsg(Context paramContext, String paramString1, String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setMessage(paramString1);
    if (paramString2.trim().isEmpty())
      paramString2 = "提示";
    localBuilder.setTitle(paramString2);
    localBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    });
    localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    });
    localBuilder.create().show();
  }

  public static void ShowMsg(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Toast.makeText(paramContext, paramString, 1).show();
      return;
    }
    new AlertDialog.Builder(paramContext).setTitle("消息(Message)").setMessage(paramString).setPositiveButton("确定(OK)", null).show();
  }

 

  public static Bitmap createImageThumbnail(Bitmap paramBitmap)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int k = Math.round(i / 300);
    int m = Math.round(j / 300);
    if ((k <= 1) && (m <= 1))
      return paramBitmap;
    if (k > m);
    for (int n = k; ; n = m)
      return ThumbnailUtils.extractThumbnail(paramBitmap, i / n, j / n);
  }

  public static byte[] createImageThumbnailbyte(String paramString)
  {
    byte[] arrayOfByte = new byte[0];
    if (new File(paramString).exists())
    {
      Bitmap localBitmap1 = BitmapFactory.decodeFile(paramString);
      if (localBitmap1 != null)
      {
        Bitmap localBitmap2 = createImageThumbnail(localBitmap1);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap2.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
        arrayOfByte = localByteArrayOutputStream.toByteArray();
      }
    }
    return arrayOfByte;
  }



  public static Bitmap createVideoThumbnail(String paramString)
  {
    return ThumbnailUtils.createVideoThumbnail(paramString, 1);
  }

  private Intent doPickPhotoFromGallery()
  {
    Intent localIntent = new Intent();
    localIntent.setType("image/*");
    localIntent.setAction("android.intent.action.GET_CONTENT");
    localIntent.setType("image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("outputX", 300);
    localIntent.putExtra("outputY", 300);
    localIntent.putExtra("return-data", true);
    return localIntent;
  }

  public static Bitmap drawTextAtBitmap(Bitmap paramBitmap, String paramString)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    localPaint.setColor(Color.parseColor("#dedbde"));
    localPaint.setTextSize(20.0F);
    localCanvas.drawText(paramString, 53.0F, 30.0F, localPaint);
    localCanvas.save(31);
    localCanvas.restore();
    return localBitmap;
  }

  public static Bitmap drawTextAtBitmap(Bitmap paramBitmap, String paramString, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    localPaint.setColor(Color.parseColor("#dedbde"));
    localPaint.setTextSize(20.0F);
    localCanvas.drawText(paramString, paramInt1, paramInt2, localPaint);
    localCanvas.save(31);
    localCanvas.restore();
    return localBitmap;
  }

  public static Bitmap drawableToBitmap(Drawable paramDrawable)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1);
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888; ; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }

  public static String getAppVersionName(Context paramContext)
  {
    String str = "";
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      str = localPackageInfo.versionName;
      if ((str == null) || (str.length() <= 0))
        return "";
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  public static Date getDateAfter(Date paramDate, int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.set(5, paramInt + localCalendar.get(5));
    return localCalendar.getTime();
  }

  public static Date getDateBefore(Date paramDate, int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.set(5, localCalendar.get(5) - paramInt);
    return localCalendar.getTime();
  }

  public static int getFontHeight(float paramFloat)
  {
    Paint localPaint = new Paint();
    localPaint.setTextSize(paramFloat);
    Paint.FontMetrics localFontMetrics = localPaint.getFontMetrics();
    return 2 + (int)Math.ceil(localFontMetrics.descent - localFontMetrics.top);
  }

  public static int getFontHeight(Paint paramPaint)
  {
    new Paint();
    Paint.FontMetrics localFontMetrics = paramPaint.getFontMetrics();
    return 2 + (int)Math.ceil(localFontMetrics.descent - localFontMetrics.top);
  }

  



  public static Date getNowDate()
  {
    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return localSimpleDateFormat.parse(localSimpleDateFormat.format(localDate), new ParsePosition(8));
  }

  public static String getNumberCN(String paramString)
  {
    String str = "";
    String[] arrayOfString = { "０", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    for (int i = 0; i < paramString.length(); i++)
    {
      if (!isNumeric(paramString.substring(i, 1)))
        continue;
      int j = Integer.parseInt(paramString.substring(i, 1));
      str = str + arrayOfString[j];
    }
    return str;
  }

 
  public static String getStringDateShort()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  public static String getStringToday()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyyMMdd HHmmss").format(localDate);
  }

  public static String getTimeShort()
  {
    return new SimpleDateFormat("HH:mm:ss").format(new Date());
  }

  public static long getTwoDay(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      Date localDate1 = localSimpleDateFormat.parse(paramString1);
      Date localDate2 = localSimpleDateFormat.parse(paramString2);
      long l = Math.abs((localDate1.getTime() - localDate2.getTime()) / 86400000L);
      return l;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public static String getTwoHour(String paramString1, String paramString2)
  {
    String[] arrayOfString1 = paramString1.split(":");
    String[] arrayOfString2 = paramString2.split(":");
    if (Integer.parseInt(arrayOfString1[0]) < Integer.parseInt(arrayOfString2[0]))
      return "0";
    double d1 = Double.parseDouble(arrayOfString1[0]) + Double.parseDouble(arrayOfString1[1]) / 60.0D;
    double d2 = Double.parseDouble(arrayOfString2[0]) + Double.parseDouble(arrayOfString2[1]) / 60.0D;
    if (d1 - d2 > 0.0D)
      return d1 - d2 + "";
    return "0";
  }

  public static String getUserDate(String paramString)
  {
    Date localDate = new Date();
    return new SimpleDateFormat(paramString).format(localDate);
  }

  public static boolean hasSdcard()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
  }

  public static boolean isDateFormat(String paramString)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      localSimpleDateFormat.setLenient(false);
      localSimpleDateFormat.parse(paramString);
      return true;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return false;
  }

  public static boolean isDouble(String paramString)
  {
    return Pattern.compile("^[-\\+]?[.\\d]*$").matcher(paramString).matches();
  }

  public static boolean isInteger(String paramString)
  {
    return Pattern.compile("^[-\\+]?[\\d]*$").matcher(paramString).matches();
  }

  public static final boolean isNumeric(String paramString)
  {
    if ((paramString != null) && (!"".equals(paramString.trim())))
      return paramString.matches("^[0-9]*$");
    return false;
  }

  public static boolean isNumeric1(String paramString)
  {
    int i = paramString.length();
    while (true)
    {
      i--;
      if (i < 0)
        break;
      if (!Character.isDigit(paramString.charAt(i)))
        return false;
    }
    return true;
  }

  public static boolean isNumeric2(String paramString)
  {
    return Pattern.compile("[0-9]*").matcher(paramString).matches();
  }

  public static boolean isNumeric3(String paramString)
  {
    int i = paramString.length();
    while (true)
    {
      i--;
      if (i < 0)
        break;
      int j = paramString.charAt(i);
      if ((j < 48) || (j > 57))
        return false;
    }
    return true;
  }

  

  // ERROR //
  public static void savePhotoToSDCard(String paramString1, String paramString2, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: invokestatic 607	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   3: ldc_w 609
    //   6: invokevirtual 481	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   9: ifeq +106 -> 115
    //   12: new 62	java/io/File
    //   15: dup
    //   16: new 64	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 65	java/lang/StringBuilder:<init>	()V
    //   23: invokestatic 71	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   26: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   29: ldc 77
    //   31: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: aload_0
    //   35: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: ldc 77
    //   40: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: invokespecial 87	java/io/File:<init>	(Ljava/lang/String;)V
    //   49: astore_3
    //   50: aload_3
    //   51: invokevirtual 91	java/io/File:exists	()Z
    //   54: ifne +8 -> 62
    //   57: aload_3
    //   58: invokevirtual 710	java/io/File:mkdirs	()Z
    //   61: pop
    //   62: new 62	java/io/File
    //   65: dup
    //   66: aload_0
    //   67: aload_1
    //   68: invokespecial 713	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: astore 4
    //   73: aconst_null
    //   74: astore 5
    //   76: new 96	java/io/FileOutputStream
    //   79: dup
    //   80: aload 4
    //   82: invokespecial 714	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   85: astore 6
    //   87: aload_2
    //   88: ifnull +22 -> 110
    //   91: aload_2
    //   92: getstatic 265	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   95: bipush 100
    //   97: aload 6
    //   99: invokevirtual 27	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   102: ifeq +8 -> 110
    //   105: aload 6
    //   107: invokevirtual 715	java/io/FileOutputStream:flush	()V
    //   110: aload 6
    //   112: invokevirtual 716	java/io/FileOutputStream:close	()V
    //   115: return
    //   116: astore 7
    //   118: aload 7
    //   120: invokevirtual 37	java/io/IOException:printStackTrace	()V
    //   123: return
    //   124: astore 8
    //   126: aload 4
    //   128: invokevirtual 719	java/io/File:delete	()Z
    //   131: pop
    //   132: aload 8
    //   134: invokevirtual 720	java/io/FileNotFoundException:printStackTrace	()V
    //   137: aload 5
    //   139: invokevirtual 716	java/io/FileOutputStream:close	()V
    //   142: return
    //   143: astore 12
    //   145: aload 12
    //   147: invokevirtual 37	java/io/IOException:printStackTrace	()V
    //   150: return
    //   151: astore 13
    //   153: aload 4
    //   155: invokevirtual 719	java/io/File:delete	()Z
    //   158: pop
    //   159: aload 13
    //   161: invokevirtual 37	java/io/IOException:printStackTrace	()V
    //   164: aload 5
    //   166: invokevirtual 716	java/io/FileOutputStream:close	()V
    //   169: return
    //   170: astore 15
    //   172: aload 15
    //   174: invokevirtual 37	java/io/IOException:printStackTrace	()V
    //   177: return
    //   178: astore 9
    //   180: aload 5
    //   182: invokevirtual 716	java/io/FileOutputStream:close	()V
    //   185: aload 9
    //   187: athrow
    //   188: astore 10
    //   190: aload 10
    //   192: invokevirtual 37	java/io/IOException:printStackTrace	()V
    //   195: goto -10 -> 185
    //   198: astore 9
    //   200: aload 6
    //   202: astore 5
    //   204: goto -24 -> 180
    //   207: astore 13
    //   209: aload 6
    //   211: astore 5
    //   213: goto -60 -> 153
    //   216: astore 8
    //   218: aload 6
    //   220: astore 5
    //   222: goto -96 -> 126
    //
    // Exception table:
    //   from	to	target	type
    //   110	115	116	java/io/IOException
    //   76	87	124	java/io/FileNotFoundException
    //   137	142	143	java/io/IOException
    //   76	87	151	java/io/IOException
    //   164	169	170	java/io/IOException
    //   76	87	178	finally
    //   126	137	178	finally
    //   153	164	178	finally
    //   180	185	188	java/io/IOException
    //   91	110	198	finally
    //   91	110	207	java/io/IOException
    //   91	110	216	java/io/FileNotFoundException
  }

  // ERROR //
 

  public String Uri2filePath(Uri paramUri, Activity paramActivity)
  {
    Cursor localCursor = paramActivity.managedQuery(paramUri, new String[] { "_data" }, null, null, null);
    int i = localCursor.getColumnIndexOrThrow("_data");
    localCursor.moveToFirst();
    return localCursor.getString(i);
  }

  

  public Bitmap zoomBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramInt1 / i, paramInt2 / j);
    return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
  }
}

/* Location:           G:\apkssss\jp\tianxiajiapu_classes_dex2jar.jar
 * Qualified Name:     eastom.txjiapu.clsCommon
 * JD-Core Version:    0.6.0
 */