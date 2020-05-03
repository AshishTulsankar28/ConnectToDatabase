package connectViaORM;
// Generated 30 Apr, 2020 3:03:16 AM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Student generated by hbm2java
 */
@Entity
@Table(name = "student")
public class Student implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6044165374960548760L;
	private int studId;
	private String studNm;
	private String studDiv;
	private String address;

	public Student() {
	}

	public Student(int studId) {
		this.studId = studId;
	}

	public Student(int studId, String studNm, String studDiv, String address) {
		this.studId = studId;
		this.studNm = studNm;
		this.studDiv = studDiv;
		this.address = address;
	}

	@Id

	@Column(name = "studId", unique = true, nullable = false)
	public int getStudId() {
		return this.studId;
	}

	public void setStudId(int studId) {
		this.studId = studId;
	}

	@Column(name = "studNm", length = 45)
	public String getStudNm() {
		return this.studNm;
	}

	public void setStudNm(String studNm) {
		this.studNm = studNm;
	}

	@Column(name = "studDiv", length = 45)
	public String getStudDiv() {
		return this.studDiv;
	}

	public void setStudDiv(String studDiv) {
		this.studDiv = studDiv;
	}

	@Column(name = "address", length = 45)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
