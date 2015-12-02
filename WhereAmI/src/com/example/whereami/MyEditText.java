package com.example.whereami;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText{
	
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;
	
	public MyEditText (Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	public MyEditText (Context context){
		super(context);
		init();
	}
	
	public MyEditText (Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	private void init(){
		//get a reference to our resource table
		Resources myResources = getResources();
		
		//create the paint brushes we will use in the onDraw method
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myResources.getColor(R.color.margin));
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResources.getColor(R.color.lines));

		//Get the paper background color and the margin width
		paperColor = myResources.getColor(R.color.background);
		margin = myResources.getDimension(R.dimen.ride_sharing_margin);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		//color as background
		canvas.drawColor(paperColor);
		
		//draw lines
		canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
		
		//Draw margin
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		
		//move the text across from the margin
		canvas.save();canvas.translate(margin, 0);
		
		//use textView to render the text
		super.onDraw(canvas);
		canvas.restore();
	}
}
