package com.ssafy.sub.dto;

import java.util.Date;

public class FeedDto {

	private int id;
	private int uid;
	private String title;
	private String content;
	private Date regdate;
	private Date editdate;
	
	public FeedDto() {
		super();
	}

	public FeedDto(int id, int uid, String title, String content, Date regdate, Date editdate) {
		super();
		this.id = id;
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
		this.editdate = editdate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getEditdate() {
		return editdate;
	}

	public void setEditdate(Date editdate) {
		this.editdate = editdate;
	}

	@Override
	public String toString() {
		return "FeedDto [id=" + id + ", uid=" + uid + ", title=" + title + ", content=" + content + ", regdate="
				+ regdate + ", editdate=" + editdate + "]";
	}
		
}
