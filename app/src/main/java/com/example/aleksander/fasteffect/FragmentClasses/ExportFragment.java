package com.example.aleksander.fasteffect.FragmentClasses;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aleksander.fasteffect.AdditionalClasses.DatabaseClasses.SQLDatabaseStructure;
import com.example.aleksander.fasteffect.R;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.NoArgsConstructor;

import static com.example.aleksander.fasteffect.Repository.DatabaseQuery.findAll;
import static java.util.Objects.requireNonNull;

/**
 * {@link Fragment}
 * Klasa przeznaczona do importu danych z aplikacji
 * Zakladka "Przeslij dziennik"
 */
@NoArgsConstructor
public class ExportFragment extends Fragment{

   private static final String TAG = "com.example.aleksander.fasteffect.FragmentClass" ;

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_export, container, false);

        sqLiteDatabase = requireNonNull(getActivity()).openOrCreateDatabase(SQLDatabaseStructure.DATABASE_FILE, Context.MODE_PRIVATE, null);

        Button buttonExport = view.findViewById(R.id.d_export);
        buttonExport.setOnClickListener(export -> exportData());
        return view;
    }

    /**
     * Metoda exportujaca dane
     */
    private void exportData() {

        Log.i(TAG,"exportData - export bazy danych");

        Cursor cursor = sqLiteDatabase.rawQuery(findAll(), null);
        cursor.moveToFirst();

        StringBuilder data = new StringBuilder("Data, Pora dnia, Nazwa, Kalorie, Ilość, Białko, Weglowodany, Tłuszcze, Błonnik");
        for (int i = 0; i < cursor.getCount(); i++) {
            String message = (
                    cursor.getString(0) + ", " +
                            cursor.getString(1) + ", " +
                            cursor.getString(2) + ", " +
                            cursor.getString(3) + ", " +
                            cursor.getString(4) + ", " +
                            cursor.getString(5) + ", " +
                            cursor.getString(6) + ", " +
                            cursor.getString(7) + ", " +
                            cursor.getString(8));
            data.append("\n").append(message);
            cursor.moveToNext();
        }

        FileOutputStream outputStream;
        try {
            outputStream = requireNonNull(getActivity()).openFileOutput("Dziennik", Context.MODE_PRIVATE);
            outputStream.write((data.toString()).getBytes());
            outputStream.close();
            Context context = getActivity().getApplicationContext();
            File fileLocation = new File(getActivity().getFilesDir(), "data");
            Uri path = FileProvider.getUriForFile(context, "com.example.aleksander.fasteffect.fileprovider", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/*");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Dziennik");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Wyślij albo zapisz swój dziennik"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            cursor.close();
        }

    }



}
