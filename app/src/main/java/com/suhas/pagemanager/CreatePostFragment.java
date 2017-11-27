package com.suhas.pagemanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreatePostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePostFragment extends DialogFragment implements GraphAPIHelper.OnCreatePostListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mPageId";
    private static final String ARG_PARAM2 = "mPageAccessToken";

    // TODO: Rename and change types of parameters
    private String mPageId;
    private String mPageAccessToken;

    private EditText mEditText;
    private Switch publishedSwitch;
    private Button mButton;

    private OnFragmentInteractionListener mListener;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageid  Parameter 1.
     * @param pageacesstoken Parameter 2.
     * @return A new instance of fragment CreatePostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePostFragment newInstance(String pageid, String pageacesstoken) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, pageid);
        args.putString(ARG_PARAM2, pageacesstoken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageId = getArguments().getString(ARG_PARAM1);
            mPageAccessToken = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_create_post, container, false);
        mEditText = (EditText) mView.findViewById(R.id.post_edit_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mButton.setEnabled(charSequence.length()!=0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mButton = (Button) mView.findViewById(R.id.post_button);
        publishedSwitch = (Switch) mView.findViewById(R.id.switch1);

        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           onButtonPressed();
                                       }
                                   });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        Bundle params = new Bundle();

        params.putString("message", mEditText.getText().toString());
        params.putBoolean("published", publishedSwitch.isChecked());
        GraphAPIHelper.postMessage(mPageAccessToken, mPageId, params, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreatePostSuccess() {
        Toast.makeText(getContext(), "create post success", Toast.LENGTH_SHORT).show();
        this.dismiss();


    }

    @Override
    public void onCreatePostFailure() {
        Toast.makeText(getContext(), "create post failure", Toast.LENGTH_SHORT).show();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
