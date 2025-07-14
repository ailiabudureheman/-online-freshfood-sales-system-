package com.mall.dao;

import java.util.List;

import com.mall.po.NotePager;

public interface AdminNoteDao {

	public List getAllNotes();
	public boolean deleteNote(int[] i);
	public NotePager getNotePager(int index, int pageSize);
}
