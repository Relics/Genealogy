/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  *//*

package eastom.txjiapu;

import android.view.View;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import java.util.List;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import android.widget.ProgressBar;
import android.widget.AdapterView;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.HashMap;
import java.util.Map;
import android.util.DisplayMetrics;
import android.widget.HorizontalScrollView;
import android.view.WindowManager;
import android.view.Display;
import java.io.File;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.graphics.Bitmap;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Window;
import android.app.Application;
import android.widget.TextView;
import android.widget.SpinnerAdapter;
import android.widget.ImageButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ZRTreeActivity extends ActionBarActivity {
	private ArrayAdapter<String> adapter;
	public float mmBdNoteFontSize;
	public float mmBodyFontSize;
	public float mmCardWidth;
	public String mmJPID;
	public String mmJPTitle;
	public float mmLeftTitleW;
	public int mmMaxLevel;
	public int mmPageH;
	public int mmPageW;
	public int mmShowType;
	public List<clsJiaZurenInfo> mmTreeA;
	public String mmZRID;
	public String mmZRTitle;
	private ProgressBar pBar;
	private RadioGroup radioGroup;
	private Spinner spinner;

	public ZRTreeActivity() {
		mmJPID = "";
		mmJPTitle = "";
		mmZRID = "";
		mmZRTitle = "";
		mmPageW = 0x3e8;
		mmPageH = 0x320;
		mmMaxLevel = 0x6;
		mmLeftTitleW = 0.0f;
		mmTreeA = new ArrayList();
		mmCardWidth = 0.0f;
		mmShowType = 0x1;
		mmBodyFontSize = 0.0f;
		mmBdNoteFontSize = 0.0f;
		radioGroup = 0x0;
	}

	private static final String[] mBeishu = new String[] {"3", "5", "6", "7", "8", "9", "10", "15", "20", "30", "50", "100"}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(0x7f030028);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(0x3);
		MyApp mApp = (MyApp) getApplication();
		mmJPID = mApp.getSelJPId();
		mmJPTitle = mApp.getSelJPTitle();
		mmZRID = mApp.getSelZRId();
		mmZRTitle = mApp.getSelZRTitle();
		pBar = (ProgressBar) findViewById(0x7f0800ed);
		pBar.setVisibility(0x4);
		TextView myTextView = (TextView) findViewById(0x7f080090);
		myTextView.setText(mmJPTitle + " - " + mmZRTitle);
		mmTreeA = clsJiaZurenTree.LoadAllPerCards(Long.parseLong(mmJPID), mmMaxLevel);
		iniLoadCards();
		radioGroup = (RadioGroup) findViewById(0x7f0800e5);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(this) {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id = group.getCheckedRadioButtonId();
				switch (id) {
				case 2131230951: {
					mmShowType = 0x1;
					break;
				}
				case 2131230950: {
					mmShowType = 0x2;
					break;
				}
				}
			}
		});
		spinner = (Spinner) findViewById(0x7f0800ea);
		adapter = new ArrayAdapter(this, 0x1090008, mBeishu);
		adapter.setDropDownViewResource(0x1090009);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new ZRTreeActivity.SpinnerSelectedListener(this));
		spinner.setSelection(0x2);
		spinner.setVisibility(0x0);
		ImageButton tBtn1 = (ImageButton) findViewById(0x7f0800eb);
		tBtn1.setOnClickListener(new View.OnClickListener(this) {

			public void onClick(View v) {
				ViewBMP();
			}
		});
	}

	class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

		public void onItemSelected(AdapterView parent, View v, int position, long id) {
			int _MaxLevel = Integer.parseInt(access$100()[position]);
			if (_MaxLevel != mmMaxLevel) {
				mmMaxLevel = _MaxLevel;
				mmTreeA = clsJiaZurenTree.LoadAllPerCards(Long.parseLong(mmJPID), mmMaxLevel);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private void iniLoadCards() {
		pBar.setVisibility(0x0);
		pBar.setProgress(0xa);
		HashMap<String, String> _CardSet = new HashMap<String, String>();
		Paint paint1 = new Paint();
		paint1.setAntiAlias(true);
		paint1.setTextSize(mmBodyFontSize);
		float tW = paint1.measureText("\u6b27");
		float tH = (float) clsCommon.getFontHeight(paint1);
		if (mmShowType == 0x1) {
			_CardSet.put("cardwidth", String.valueOf((4.0f * tW)));
			_CardSet.put("cardheight", String.valueOf(tH));
		} else {
			_CardSet.put("cardwidth", String.valueOf(((double) tW * 1.5)));
			_CardSet.put("cardheight", String.valueOf((4.0f * tW)));
		}
		mmTreeA = clsJiaZurenTree.RelayoutCards(mmTreeA, mmMaxLevel, _CardSet, _pW, _pH);
		mmPageW = (int) (Float.parseFloat(_pW.toString()) + mmLeftTitleW);
		mmPageH = (int) Float.parseFloat(_pH.toString());
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenH = displayMetrics.heightPixels;
		int screenW = displayMetrics.widthPixels;
		if (mmPageW < (screenW - 0x5)) {
			mmPageW = (screenW - 0x5);
		}
		if (mmPageH < (screenH - 0x1e)) {
			mmPageH = (screenH - 0x1e);
		}
		HorizontalScrollView textLinearLayout1 = (HorizontalScrollView) findViewById(0x7f0800ef);
		textLinearLayout1.removeAllViews();
		textLinearLayout1.addView(new ZRTreeActivity.CustomView1(this, this));
		pBar.setProgress(0x64);
	}

	public void ViewBMP() {
		// :( Parsing error. Please contact me.
	}

	public void ViewSave2BMP() {
        HorizontalScrollView textLinearLayout1 = (HorizontalScrollView)findViewById(0x7f0800ef);
        View view = textLinearLayout1.getChildAt(0x0);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        String _Filename = clsCommon.createDTimeFileName("png");
        File photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), _Filename);
        FileOutputStream fileOutputStream = 0x0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
            if(bitmap != null) {
                if(bitmap.compress(Bitmap.CompressFormat.PNG, 0x64, fileOutputStream)) {
                    fileOutputStream.flush();
                    clsCommon.ShowMsg(this, "\u56fe\u7247\u4fdd\u5b58\u5230" + photoFile.getAbsolutePath(), true);
                    sendShare(photoFile);
                }
            } catch(FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch(IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                    return;
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Parsing error may occure here :(
    }

	private void sendShare(File photoFile) {
		String tDscr = "\u60a8\u597d\uff01\u6211\u4eec\u6b63\u5728\u6574\u7406\u5bb6\u8c31\u8d44\u6599\uff0c\u73b0\u628a\u5bb6\u8c31\u56fe\u7247\u53d1\u7ed9\u4f60\u53c2\u8003\uff0c\u82e5\u6709\u9519\u6f0f\u8bf7\u53ca\u65f6\u4e0e\u6211\u4eec\u8054\u7cfb\u3002\n\u3010\u5929\u4e0b\u5bb6\u8c31\u3011\u624b\u673a\u5e94\u7528 www.Jiazu168.com";
		Intent intent = new Intent("android.intent.action.SEND");
		intent.setType("text/plain");
		if (photoFile != null) {
			intent.setType("image/*");
			Uri uri = Uri.fromFile(photoFile);
			intent.putExtra("android.intent.extra.STREAM", uri);
		}
		intent.putExtra("android.intent.extra.SUBJECT", "\u4eb2\u7cfb\u56fe\u5206\u4eab");
		intent.putExtra("android.intent.extra.TEXT", tDscr);
		intent.putExtra("android.intent.extra.TITLE", "\u5bb6\u8c31\u4eb2\u7cfb\u56fe");
		intent.setFlags(0x10000000);
		startActivity(Intent.createChooser(intent, "\u8bf7\u9009\u62e9"));
	}

	class CustomView1 extends View {
		Paint paint;

		public CustomView1(ZRTreeActivity this$0, Context context) {
			super(context);
			paint = new Paint();
			paint.setColor(0xff000000);
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			setMeasuredDimension(mmPageW, mmPageH);
		}

		protected void onDraw(Canvas canvas) {
			DrawTree(canvas);
		}

		public void DrawTree(Canvas canvas) {
            canvas.drawColor(-0x1);
            paint.setAntiAlias(true);
            paint.setTextSize(18.0f);
            paint.setColor(-0xffff01);
            paint.setTextAlign(Paint.Align.CENTER);
            int tTitleFontSize = 0x12;
            int tRemarkFontSize = 0xc;
            int CardBkColor = Color.argb(0xff, 0xcb, 0xff, 0xac);
            int CardBkSpColor = Color.argb(0xff, 0xff, 0xbb, 0x6d);
            int CardTextColor1 = 0xff000000;
            int CardLineSpColor = Color.argb(0xff, 0xcb, 0x5e, 0x48);
            int CardLineChColor = Color.argb(0xff, 0xd, 0xc2, 0xc2);
            int CardLineNotesColor = Color.argb(0xff, 0x62, 0x4c, 0xc3);
            float GapY = 1.0f;
            float GapX = 1.0f;
            float X1 = 0x0;
            float Y1 = (float)clsCommon.getFontHeight(paint);
            float tY = 0x0;
            float tX = 0x0;
            float tTextW = 0x0;
            float tLeftTitleW = mmLeftTitleW;
            tX = paint.measureText(mmJPTitle);
            X1 = ((float)mmPageW - tX) / 2.0f;
            canvas.drawText(mmJPTitle, (float)(mmPageW / 0x2), Y1, paint);
            Y1 += (3.0f * GapY);
            canvas.drawLine(X1, Y1, (X1 + tX), Y1, this);
            Y1 += ((float)clsCommon.getFontHeight((float)tTitleFontSize) + GapY);
            paint.setTextSize((float)0x16);
            paint.setColor(-0xff0100);
            canvas.drawText(mmZRTitle + " \u5b50\u5b59\u56fe", (float)(mmPageW / 0x2), Y1, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize((float)tRemarkFontSize);
            paint.setColor(CardLineSpColor);
            X1 = paint.measureText("\u914d\u5076\u7ebf");
            Y1 += ((float)clsCommon.getFontHeight((float)tRemarkFontSize) + GapY);
            canvas.drawText("\u914d\u5076\u7ebf", ((float)mmPageW - (3.0f * X1)), Y1, paint);
            canvas.drawLine(((((float)mmPageW - (3.0f * X1)) + X1) + 4.0f), (Y1 - 5.0f), (((float)mmPageW - (3.0f * X1)) + (2.0f * X1)), (Y1 - 5.0f), this);
            paint.setColor(CardLineChColor);
            Y1 += ((float)clsCommon.getFontHeight((float)tRemarkFontSize) + GapY);
            canvas.drawText("\u5b50\u5973\u7ebf", ((float)mmPageW - (3.0f * X1)), Y1, paint);
            canvas.drawLine(((((float)mmPageW - (3.0f * X1)) + X1) + 4.0f), (Y1 - 5.0f), (((float)mmPageW - (3.0f * X1)) + (2.0f * X1)), (Y1 - 5.0f), this);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(mmBodyFontSize);
            paint.setColor(CardTextColor1);
            tTextW = paint.measureText("\u6768");
            if(mmTreeA != null) {
                continue;
                ) {
                    String tName = (clsJiaZurenInfo)mmTreeA.get(i)._FullName;
                    X1 = (clsJiaZurenInfo)mmTreeA.get(i)._Left + tLeftTitleW;
                    float Y1 = (clsJiaZurenInfo)mmTreeA.get(i)._Top;
                    boolean _tSameSurname = (clsJiaZurenInfo)mmTreeA.get(i)._SameSurname;
                    RectF targetRect = new RectF(X1, Y1, (X1 + (clsJiaZurenInfo)mmTreeA.get(i)._Width), ((clsJiaZurenInfo)mmTreeA.get(i)._Height + Y1));
                    if(_tSameSurname) {
                        paint.setColor(CardBkColor);
                    } else {
                        paint.setColor(CardBkSpColor);
                    }
                    canvas.drawRect(targetRect, paint);
                    paint.setColor(CardTextColor1);
                    paint.setTextAlign(Paint.Align.CENTER);
                    if(mmShowType == 0x1) {
                        canvas.drawText(tName, targetRect.centerX(), (Y1 + tTextW), paint);
                    }
                    ShowVertText(canvas, tName, targetRect.centerX(), (Y1 + tTextW), this);
                }
                int ttLevel = 0x0;
                int ttLevel2 = 0x0;
                ArrayList<String> ttListChild = new ArrayList<String>();
                ArrayList<String> ttListSpouse = new ArrayList<String>();
                ArrayList<String> ttListSpecial = new ArrayList<String>();
                for(; i >= 0; i = i - 0x1) {
                    pBar.setProgress(((i / mmTreeA.size()) * pBar.getMax()));
                    String ttID1 = (clsJiaZurenInfo)mmTreeA.get(i)._PersonId;
                    String ttName1 = (clsJiaZurenInfo)mmTreeA.get(i)._FullName;
                    ttLevel = (clsJiaZurenInfo)mmTreeA.get(i)._Level;
                    ttSpouseRow = (clsJiaZurenInfo)mmTreeA.get(i)._SpouseRow;
                    float ttX1 = (clsJiaZurenInfo)mmTreeA.get(i)._Left + tLeftTitleW;
                    float ttY1 = (clsJiaZurenInfo)mmTreeA.get(i)._Top;
                    float ttW1 = (clsJiaZurenInfo)mmTreeA.get(i)._Width;
                    float ttH1 = (clsJiaZurenInfo)mmTreeA.get(i)._Height;
                    ttListSpecial = (clsJiaZurenInfo)mmTreeA.get(i)._SpecialRels;
                    if(ttSpouseRow == "1") {
                        paint.setColor(CardLineChColor);
                        for(int a = i; a >= 0; a = a - 0x1) {
                            String ttID2 = (clsJiaZurenInfo)mmTreeA.get(a)._PersonId;
                            ttLevel2 = (clsJiaZurenInfo)mmTreeA.get(a)._Level;
                            ttSpouseRow2 = (clsJiaZurenInfo)mmTreeA.get(a)._SpouseRow;
                            ttListChild = (clsJiaZurenInfo)mmTreeA.get(a)._ChildID;
                            localint1 = ttListChild.contains(ttID1)(ttSpouseRow2 != "2" ? 0x1 : 0x0 != null)) {
                                float ttX2 = (clsJiaZurenInfo)mmTreeA.get(a)._Left + tLeftTitleW;
                                float ttY2 = (clsJiaZurenInfo)mmTreeA.get(a)._Top;
                                float ttW2 = (clsJiaZurenInfo)mmTreeA.get(a)._Width;
                                float ttH2 = (clsJiaZurenInfo)mmTreeA.get(a)._Height;
                                if(mmShowType == 0x1) {
                                    canvas.drawLine((ttX1 + (ttW1 / 2.0f)), ttY1, (ttX2 + (ttW2 / 2.0f)), (ttY2 + ttH2), this);
                                } else {
                                    canvas.drawLine((ttX1 + (ttW1 / 2.0f)), ttY1, (ttX2 + (ttW2 / 2.0f)), (ttY2 + ttH2), this);
                                }
                                if(ttListSpecial != null) {
                                    continue;
                                    ) {
                                        break;
                                        if((String)ttListSpecial.get(p).indexOf(ttID2) >= 0) {
                                            Paint paint2 = new Paint();
                                            paint2.setColor(CardLineNotesColor);
                                            paint2.setTextSize(mmBdNoteFontSize);
                                            ttSpStr = (String)ttListSpecial.get(p).substring((ttID2.length() + 0x1));
                                            if(mmShowType == 0x1) {
                                                ttSpStr = "(" + ttSpStr + ")";
                                                canvas.drawText(ttSpStr, ((ttW1 / 2.0f) + ttX1), ttY1, paint2);
                                            }
                                            ttSpStr = "" + ttSpStr + "";
                                            ShowVertText(canvas, ttSpStr, (ttX1 + ttW1), ttY1, this);
                                        }
                                    }
                                }
                                if(ttListChild.contains(ttID1)) {
                                    ttX2 = (clsJiaZurenInfo)mmTreeA.get(a)._Left + tLeftTitleW;
                                    ttY2 = (clsJiaZurenInfo)mmTreeA.get(a)._Top;
                                    ttW2 = (clsJiaZurenInfo)mmTreeA.get(a)._Width;
                                    ttH2 = (clsJiaZurenInfo)mmTreeA.get(a)._Height;
                                    if(mmShowType == 0x1) {
                                        canvas.drawLine((ttX1 + (ttW1 / 2.0f)), ttY1, (ttX2 + (ttW2 / 2.0f)), (ttY2 + ttH2), this);
                                    } else {
                                        canvas.drawLine((ttX1 + (ttW1 / 2.0f)), ttY1, (ttX2 + (ttW2 / 2.0f)), (ttY2 + ttH2), this);
                                    }
                                    if(ttListSpecial != null) {
                                        continue;
                                        ) {
                                            if((String)ttListSpecial.get(p).indexOf(ttID2) >= 0) {
                                                Paint paint2 = new Paint();
                                                paint2.setColor(CardLineNotesColor);
                                                paint2.setTextSize(mmBdNoteFontSize);
                                                ttSpStr = (String)ttListSpecial.get(p).substring((ttID2.length() + 0x1));
                                                if(mmShowType == 0x1) {
                                                    ttSpStr = "(" + ttSpStr + ")";
                                                    canvas.drawText(ttSpStr, ((ttW1 / 2.0f) + ttX1), ttY1, paint2);
                                                }
                                                ttSpStr = "" + ttSpStr + "";
                                                ShowVertText(canvas, ttSpStr, (ttX1 + ttW1), ttY1, this);
                                            }
                                        }
                                        paint.setColor(CardLineSpColor);
                                        for(; a >= 0; a = a - 0x1) {
                                            break;
                                            ttID2 = (clsJiaZurenInfo)mmTreeA.get(a)._PersonId;
                                            ttLevel2 = (clsJiaZurenInfo)mmTreeA.get(a)._Level;
                                            ttSpouseRow2 = (clsJiaZurenInfo)mmTreeA.get(a)._SpouseRow;
                                            ttListSpouse = (clsJiaZurenInfo)mmTreeA.get(a)._SpouseID;
                                            String ttName2 = (clsJiaZurenInfo)mmTreeA.get(a)._FullName;
                                            localint1 = ttListSpouse.contains(ttID1)(ttSpouseRow2 != "1" ? 0x1 : 0x0 != null)) {
                                                ttX2 = (clsJiaZurenInfo)mmTreeA.get(a)._Left + tLeftTitleW;
                                                ttY2 = (clsJiaZurenInfo)mmTreeA.get(a)._Top;
                                                ttW2 = (clsJiaZurenInfo)mmTreeA.get(a)._Width;
                                                ttH2 = (clsJiaZurenInfo)mmTreeA.get(a)._Height;
                                                canvas.drawLine((ttX1 + (ttW1 / 2.0f)), ttY1, (ttX2 + (ttW2 / 2.0f)), (ttY2 + ttH2), this);
                                                if(ttListSpecial != null) {
                                                    continue;
                                                    ) {
                                                        break;
                                                        if((String)ttListSpecial.get(p).indexOf(ttID2) >= 0) {
                                                            Paint paint2 = new Paint();
                                                            paint2.setColor(CardLineNotesColor);
                                                            paint2.setTextSize(mmBdNoteFontSize);
                                                            ttSpStr = (String)ttListSpecial.get(p).substring((ttID2.length() + 0x1));
                                                            if(mmShowType == 0x1) {
                                                                ttSpStr = "(" + ttSpStr + ")";
                                                                canvas.drawText(ttSpStr, ((ttW1 / 2.0f) + ttX1), ttY1, paint2);
                                                            }
                                                            ttSpStr = "" + ttSpStr + "";
                                                            ShowVertText(canvas, ttSpStr, (ttX1 + ttW1), ttY1, this);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    DrawLeftTitle(canvas);
                    paint.setColor(0xff000000);
                    paint.setTextSize((float)tRemarkFontSize);
                    paint.setTextAlign(Paint.Align.LEFT);
                    String _BtNotes = "\u66f4\u591a\u4e30\u5bcc\u529f\u80fd\u8bf7\u4f7f\u7528\u3010\u5929\u4e0b\u5bb6\u8c31\u3011\u7535\u8111\u7248\uff0c\u4e0b\u8f7d\u5730\u5740\uff1ahttp://www.JiaZu168.com";
                    float _bY = (float)clsCommon.getFontHeight(paint);
                    canvas.drawText(_BtNotes, 3.0f, (((float)mmPageH - _bY) - 2.0f), paint);
                    pBar.setProgress(0x64);
                }
                // Parsing error may occure here :(
            }
            // Parsing error may occure here :(
        }

		private void DrawLeftTitle(Canvas canvas) {
            int iLayer = mmMaxLevel;
            int tLeftTitleFontSize = 0x10;
            List<clsJiaZurenInfo> tListA = mmTreeA;
            clsJiaZurenInfo tZR = new clsJiaZurenInfo();
            for(int h = 0x0; h <= iLayer; h = h + 0x1) {
                for(int k = 0x0; k < tListA.size(); k = k + 0x1) {
                    break;
                    tZR = tListA.get(k);
                    (0x0 != 0)) {
                        String tLevelTitle = "";
                        float X1 = 10.0f;
                        float Y1 = 0x0;
                        String _LevelS = clsCommon.getNumberCN(String.valueOf(tZR._Level));
                        tLevelTitle = "\u7b2c" + _LevelS + "\u4ee3";
                        float Y1 = tZR._Top;
                        paint.setColor(Color.argb(0xff, 0xfd, 0x69, 0x66));
                        paint.setTextSize((float)tLeftTitleFontSize);
                        paint.setTextAlign(Paint.Align.LEFT);
                        float _bY = (float)clsCommon.getFontHeight(paint);
                        if(mmShowType == 0x1) {
                            canvas.drawText(tLevelTitle, X1, (Y1 + _bY), paint);
                            break;
                        }
                        ShowVertText(canvas, tLevelTitle, X1, (Y1 + _bY), this);
                    }
                }
            }
        }

		private void ShowVertTextbyChar(Canvas canvas, String text, float left, float top, Paint tPaint) {
			Path path = new Path();
			path.moveTo((left - 10.0f), (top - 10.0f));
			path.lineTo((left - 10.0f), (40.0f + top));
			int len = text.length();
			float py = top;
			float py1 = 0x0;
			for (int i = 0x0; i < len; i = i + 0x1) {
				char c = text.charAt(i);
				float w = tPaint.measureText(text, i, (i + 0x1));
				StringBuffer b = new StringBuffer();
				b.append(c);
				if (clsCommon.isChinese(c)) {
					canvas.drawText(b.toString(), left, py, tPaint);
					py += w;
					continue;
				}
				canvas.drawTextOnPath(b.toString(), this, this, 0.0f, tPaint);
				py1 += (0.5f + w);
			}
		}

		private void ShowVertText(Canvas canvas, String text, float left, float top, Paint tPaint) {
			int len = text.length();
			float py = top;
			char c = text.charAt(0x0);
			if (clsCommon.isChinese(c)) {
				tPaint.setTextAlign(Paint.Align.CENTER);
				for (int i = 0x0; i < len; i = i + 0x1) {
					c = text.charAt(i);
					float w = tPaint.measureText(text, i, (i + 0x1));
					StringBuffer b = new StringBuffer();
					b.append(c);
					canvas.drawText(b.toString(), left, py, tPaint);
					py += w;
				}
				float tTxtW2 = tPaint.measureText("X") / 2.0f;
				Path path = new Path();
				path.moveTo((left - tTxtW2), (top - tTxtW2));
				path.lineTo((left - tTxtW2), (40.0f + top));
				tPaint.setTextAlign(Paint.Align.LEFT);
				canvas.drawTextOnPath(text, this, 0.0f, 0.0f, tPaint);
				break;
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(0x7f0c0014, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 0x7f080102) {
			return true;
		}
		if (id == 0x7f08010f) {
			ViewSave2BMP();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
*/