package br.com.neki.neki_skills.dto.imgbb;

public class ImgBBDTO {
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ImgBBDTO [data=" + data + "]";
	}
}