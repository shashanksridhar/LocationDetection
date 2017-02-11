package com.tk.mapoi.fragments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.mapoi.R;

/**
 * The Class InformationFragment - UI for Info option in Left Panel
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class InformationFragment extends Fragment {

	/**
	 * Instantiates a new information fragment.
	 */
	public InformationFragment() {
	}

	/** The tv. */
	TextView tv;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_information,
				container, false);

		tv = (TextView) rootView.findViewById(R.id.textViewInfoPage);

		tv.setText(readtext());

		return rootView;
	}

	/**
	 * Readtext.
	 * 
	 * @return the string
	 */
	private String readtext() {

		InputStream inputstream = getResources().openRawResource(R.raw.mapoi);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int i;

		try {

			i = inputstream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputstream.read();
			}
			inputstream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return byteArrayOutputStream.toString();
	}

}
