package model;

public class Document {
	private String title;
	private String author;
	private String text;
	private String privacy;

	public Document(String title, String author, String text, String privacy) {
		this.title = title;
		this.author = author;
		this.text = text;
		this.privacy = privacy;
	}

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	@Override
	public String toString() {
		return "Document [title=" + title + ", author=" + author + ", text=" + text + ", privacy=" + privacy + "]";
	}
	
}
