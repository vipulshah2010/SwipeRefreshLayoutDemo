package com.example.swiperefreshlayoutdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public static class PlaceholderFragment extends Fragment implements
			OnRefreshListener {

		private SwipeRefreshLayout swipeRefreshLayout;
		private ListView colorsList;
		private ArrayAdapter<String> colorAdapter;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
			return swipeRefreshLayout;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			colorsList = new ListView(getActivity());
			colorAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1);
			colorAdapter.add("Red");
			colorAdapter.add("Green");
			colorAdapter.add("Blue");
			colorsList.setAdapter(colorAdapter);
			swipeRefreshLayout.addView(colorsList);
			swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
					android.R.color.holo_green_dark,
					android.R.color.holo_orange_dark,
					android.R.color.holo_red_dark);
			swipeRefreshLayout.setOnRefreshListener(this);
		}

		@Override
		public void onRefresh() {
			new Thread() {
				public void run() {
					SystemClock.sleep(4000);

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							colorAdapter.add("Magenta");
							colorAdapter.add("Pink");
							colorAdapter.add("Maroon");
							swipeRefreshLayout.setRefreshing(false);
						}
					});

				};
			}.start();
		}
	}

}
