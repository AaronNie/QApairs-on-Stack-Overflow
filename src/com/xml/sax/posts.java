package com.xml.sax;

public class posts {
	
	private Float min;
	private Float max;
	
	private String Id;
	private String PostTypeId;
	private String ParentId;
	private String AcceptedAnswerId;
	private String Score;
	private String ViewCount;
	private String Body;
	private String OwnerUserId;
	private String LastEditorUserId;
	private String Title;
	private String Tags;
	private String AnswerCount;
	private String CommentCount;
	private String FavoriteCount;
	
	private String CreationDate;
	private String LastEditorDisplayName="Jeff Atwood";
	private String LastEditDate="2009-03-05T22:28:34.823";
	private String LastActivityDate="2009-03-11T12:51:01.480";
	private String CommunityOwnedDate="2009-03-11T12:51:01.480";
	private String ClosedDate="2009-03-11T12:51:01.480";
	
	public Float getmin() {
		return min;
		}
	public void setmin(Float min) {
		this.min = min;
		}
	
	public Float getmax() {
		return max;
		}
	public void setmax(Float max) {
		this.max = max;
		}
	
	public String getId() {
		return Id;
		}
	public void setId(String Id) {
		this.Id = Id;
		}

	public String getPostTypeId() {
		return PostTypeId;
		}
	public void setPostTypeId(String PostTypeId) {
		this.PostTypeId = PostTypeId;
		}
	
	public String getParentId() {
		return ParentId;
		}
	public void setParentId(String ParentId) {
		this.ParentId = ParentId;
		}
	
	public String getAcceptedAnswerId() {
		return AcceptedAnswerId;
		}
	public void setAcceptedAnswerId(String AcceptedAnswerId) {
		this.AcceptedAnswerId = AcceptedAnswerId;
		}
	
	public String getScore() {
		return Score;
		}
	public void setScore(String Score) {
		this.Score = Score;
		}
	
	public String getTitle() {
		return Title;
		}
	public void setTitle(String Title) {
		this.Title = Title;
		}
	
	public String getTags() {
		return Tags;
		}
	public void setTags(String Tags) {
		this.Tags = Tags;
		}
	
	public String getBody() {
		return Body;
		}
	public void setBody(String Body) {
		this.Body = Body;
		}
	
	public String getOwnerUserId() {
		return OwnerUserId;
		}
	public void setOwnerUserId(String OwnerUserId) {
		this.OwnerUserId = OwnerUserId;
		}
	
	public String getLastEditorUserId() {
		return LastEditorUserId;
		}
	public void setLastEditorUserId(String LastEditorUserId) {
		this.LastEditorUserId = LastEditorUserId;
		}
	
	public String getAnswerCount() {
		return AnswerCount;
		}
	public void setAnswerCount(String AnswerCount) {
		this.AnswerCount = AnswerCount;
		}
	
	public String getViewCount() {
		return ViewCount;
		}
	public void setViewCount(String ViewCount) {
		this.ViewCount = ViewCount;
		}
	
	public String getCommentCount() {
		return CommentCount;
		}
	public void setCommentCount(String CommentCount) {
		this.CommentCount = CommentCount;
		}
	
	public String getFavoriteCount() {
		return FavoriteCount;
		}
	public void setFavoriteCount(String FavoriteCount) {
		this.FavoriteCount = FavoriteCount;
		}
}
