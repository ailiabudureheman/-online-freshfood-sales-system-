package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.UserDao;
import com.mall.po.Note;
import com.mall.po.Page;
import com.mall.po.User;

public class UserDaoImpl implements UserDao{

	//用户注册
	public boolean addUser(User user) {
		boolean flag = false;
		PreparedStatement pstmt = null;
		DbUtil dbUtil = null;
		String sql = "insert into tb_user values(null,?,?,?,?,?,?,?,?,?,?,?,?,null,null)";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getTrueName());
			pstmt.setString(5, user.getSex());
			pstmt.setString(6, user.getBirthday());
			pstmt.setString(7, user.getAddress());
			pstmt.setString(8, user.getPostcode());
			pstmt.setString(9, user.getPhone());
			pstmt.setString(10, user.getMphone());
			pstmt.setString(11, user.getQuestion());
			pstmt.setString(12, user.getAnswer());
			int i = pstmt.executeUpdate();
			if(i != 0) {//注册成功 
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return flag;
	}
	//用户登录
	public User userLogin(String name,String password) {
		User user = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql ="select * from tb_user where name=?";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(!password.equals(rs.getString("password"))) {
					throw new ErrPwdException("密码不正确");
				}else {//登录成功
					user.setEmail(rs.getString("email"));
					user.setAddress(rs.getString("address"));
					user.setQuestion(rs.getString("question"));
					user.setAnswer(rs.getString("answer"));
					user.setBirthday(rs.getString("birthday"));
					user.setSex(rs.getString("sex"));
					user.setImg(rs.getString("img"));
					user.setMphone(rs.getString("mphone"));
					user.setPhone(rs.getString("phone"));
					user.setPostcode(rs.getString("postcode"));
					user.setTrueName(rs.getString("trueName"));
					return user;
				}
			}else {
				throw new NameNotFoundException("用户名不存在！！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	//显示所有用户
	public List listUser() {
		List userList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil du = null;
		String sql = "select * from tb_user";
		try {
			DbUtil dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setId(rs.getInt("id"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				du.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList;
	}
	//分页��显示所有用户��ʾ�û�
	public Page doPage(int currentPage,int pageSize){
		Page page = new Page();
		int totalNum = listUser().size();
		List pageList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil du = null;
		String sql = "select * from tb_user limit "+currentPage+","+pageSize;
		try {
			DbUtil dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				pageList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				du.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
	}	
	//根据ID删除用户
	public boolean delete(int id,String powerType){
		boolean flag = false;
		if(powerType.equals("��������Ա")) {
			PreparedStatement pstmt = null;
			DbUtil du = null;
			String sql = "delete from tb_user where id=?";
			try {
				DbUtil dbUtil = new DbUtil();
				pstmt = dbUtil.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);
				int i = pstmt.executeUpdate();
				if(i != 0){
					flag = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					pstmt.close();
					du.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public boolean changePower(int id, String powerType) {
		return false;
	}

	public String getPower(String name) {
		return null;
	}

	public User getUser(String name) {
		return null;
	}
    //显示所有用户的留言
	public List showNote(){
		List messageList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil du = null;
		String sql = "select * from tb_note";
		try {
			DbUtil dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				try{
					Note note = new Note();
					note.setAuthor(rs.getString("author"));
					note.setTitle(rs.getString("title"));
					note.setContent(rs.getString("content"));
					note.setLy_time(rs.getString("ly_time"));
					note.setImgs(rs.getString("imgs"));
					messageList.add(note);
				}catch(NoMessageException e){
					throw new NoMessageException("目前还没有用户留言！");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messageList;
	}
	/**
	 * 分页显示所有用户留言
	 * @param currentPage 显示出来的当前页码
	 * @param pageSize 每页显示数目
	 * @return Page
	 */
	public Page doNotePage(int currentPage,int pageSize){
		Page page = new Page();
		int totalNum = showNote().size();
		List pageList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "select * from tb_note limit "+currentPage+","+pageSize;
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Note note = new Note();
				note.setAuthor(rs.getString("author"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setLy_time(rs.getString("ly_time"));
				note.setImgs(rs.getString("imgs"));
				pageList.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		page.setCurrentPage(currentPage);
		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
	}
	//用户添加留言
	public boolean addNote(Note note){
		boolean flag = false;
		PreparedStatement pstmt = null;
		DbUtil dbUtil = null;
		String sql = "insert into tb_note(id,title,author,content,ly_time,imgs) values(null,?,?,?,null,?)";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, note.getTitle());
			pstmt.setString(2, note.getAuthor());
			pstmt.setString(3, note.getContent());
			pstmt.setString(4, note.getImgs());
			int i = pstmt.executeUpdate();
			if(i != 0) {//留言成功
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	//检查用户是否存在
	public boolean checkNameExist(String name){
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql ="select * from tb_user where name=?";	
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()){//用户存在
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return flag;
	}
	/* 用户旧密码 */
	public Boolean loggin(String name, String password) {
		Boolean fly = false;
		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_user where name = ? ";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, name);
			re = pre.executeQuery();
			if (re.next()) {
				 if (!re.getString("password").equals(password)) {
					 fly = false;//旧密码错误
					 }else {
						fly = true;// 旧密码正确
					 }
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
				pre.close();
				dao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fly;
	}
	/* 用于查询会员的个人信息资料 */
	public User SelectOneUser(String name) {
		User user = new User();
		DbUtil dao = new DbUtil();
		
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_user where name = ? ";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, name);
			re = pre.executeQuery();
			if (re.next()) {
				user.setName(re.getString("name"));// 从数据库中取出后在放在po对象里
				user.setPassword(re.getString("password"));
				user.setEmail(re.getString("email"));
				user.setTrueName(re.getString("trueName"));
				user.setSex(re.getString("sex"));
				user.setBirthday(re.getString("birthday"));
				user.setAddress(re.getString("address"));
				user.setPostcode(re.getString("postcode"));
				user.setPhone(re.getString("phone"));
				user.setMphone(re.getString("mphone"));
				user.setQuestion(re.getString("question"));
				user.setAnswer(re.getString("answer"));
                user.setImg(re.getString("img"));
                user.setScore(re.getInt("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	} /* 用于修改用户的个人密码 */

	public Boolean updatePassword(String name, String password) {
		Boolean fly = false;

		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		String sql = "update tb_user set  password = ?  where name = ?";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, password);
			pre.setString(2, name);
			pre.executeUpdate();
			fly = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fly;
	}

	/* 用于完善会员的 其它资料 */
	public Boolean updateUser(User user) {
		Boolean fly = false;

		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		String sql = "update tb_user set  email = ? , trueName = ? , sex = ? , birthday = ? , address = ? , postcode = ? , phone = ? , mphone = ? , question = ? , answer = ? , img = ?   where name = ?";
		try {

			pre = dao.getCon().prepareStatement(sql);
			
			pre.setString(1, user.getEmail());
			pre.setString(2, user.getTrueName());
			pre.setString(3, user.getSex());
			pre.setString(4, user.getBirthday());
			pre.setString(5, user.getAddress());
			pre.setString(6, user.getPostcode());
			pre.setString(7, user.getPhone());
			pre.setString(8, user.getMphone());
			pre.setString(9, user.getQuestion());
			pre.setString(10, user.getAnswer());
		    pre.setString(11, user.getImg());
		    pre.setString(12, user.getName());		
			pre.addBatch();
			pre.executeBatch();
			fly = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fly;
	}
}