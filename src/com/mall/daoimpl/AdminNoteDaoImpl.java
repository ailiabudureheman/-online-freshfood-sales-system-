package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.AdminNoteDao;
import com.mall.po.Note;
import com.mall.po.NotePager;

public class AdminNoteDaoImpl implements AdminNoteDao {

	public List getAllNotes() {
		List noteList = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_note";
		try {
			dao = new DbUtil();
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Note note = new Note();
				note.setId(rs.getInt("id"));
				note.setTitle(rs.getString("title"));
				note.setAuthor(rs.getString("author"));
				note.setContent(rs.getString("content"));
				note.setLy_time(rs.getString("ly_time"));
				noteList.add(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return noteList;
	}

	public boolean deleteNote(int[] ids) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "delete from tb_note where id=?";
		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(int j=0;j<ids.length;j++) {
				ps.setInt(1, ids[j]);
				ps.addBatch();
			}
			int[] k = ps.executeBatch();
			conn.commit();
			if(k.length == ids.length) {
				return true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				ps.close();
				daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public NotePager getNotePager(int index, int pageSize) {
		Map noteMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_note limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Note note = new Note();
				note.setId(rs.getInt("id"));
				note.setAuthor(rs.getString("author"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setLy_time(rs.getString("ly_time"));
				note.setImgs(rs.getString("imgs"));
				noteMap.put(note.getId(), note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		NotePager np = new NotePager();
		np.setNoteMap(noteMap);
		np.setPageSize(pageSize);
		np.setTotalNum(getAllNotes().size());
		return np;
	}

}
