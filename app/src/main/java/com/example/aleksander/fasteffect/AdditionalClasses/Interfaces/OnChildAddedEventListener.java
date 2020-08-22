package com.example.aleksander.fasteffect.AdditionalClasses.Interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Interfejs rozszerzajacy interfejs ChildEventListener
 * Ogranicza się do implementacji tylko onChildAdded z interfejsu ChildEventListener
 */
@FunctionalInterface
public interface OnChildAddedEventListener extends ChildEventListener {
    @Override
    void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s);

    @Override
    default void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
    }

    @Override
    default  void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
    }

    @Override
    default void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
    }

    @Override
    default  void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
