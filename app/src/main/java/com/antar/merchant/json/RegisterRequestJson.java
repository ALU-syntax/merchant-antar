package com.antar.merchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class RegisterRequestJson {

    @SerializedName("nama_mitra")
    @Expose
    private String nama_mitra;

    @SerializedName("jenis_identitas")
    @Expose
    private String jenis_identitas;

    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;

    @SerializedName("no_telepon")
    @Expose
    private String no_telepon;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("alamat_mitra")
    @Expose
    private String alamat_mitra;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("id_fitur")
    @Expose
    private String id_fitur;

    @SerializedName("nama_merchant")
    @Expose
    private String nama_merchant;

    @SerializedName("alamat_merchant")
    @Expose
    private String alamat_merchant;

    @SerializedName("latitude_merchant")
    @Expose
    private String latitude_merchant;

    @SerializedName("longitude_merchant")
    @Expose
    private String longitude_merchant;

    @SerializedName("jam_buka")
    @Expose
    private String jam_buka;

    @SerializedName("jam_tutup")
    @Expose
    private String jam_tutup;

    @SerializedName("category_merchant")
    @Expose
    private String category_merchant;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("foto_ktp")
    @Expose
    private String foto_ktp;

    @SerializedName("checked")
    @Expose
    private String checked;

    @SerializedName("referal_code")
    @Expose
    private String referal_code;

    @SerializedName("wilayah")
    @Expose
    private int wilayah;

    public int getWilayah() {
        return wilayah;
    }

    public void setWilayah(int wilayah) {
        this.wilayah = wilayah;
    }

    public void setNama_mitra(String nama_mitra) {
        this.nama_mitra = nama_mitra;
    }

    public String getNama_mitra() {
        return nama_mitra;
    }

    public void setJenis_identitas(String jenis_identitas) {
        this.jenis_identitas = jenis_identitas;
    }

    public String getJenis_identitas() {
        return jenis_identitas;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAlamat_mitra(String alamat_mitra) {
        this.alamat_mitra = alamat_mitra;
    }

    public String getAlamat_mitra() {
        return alamat_mitra;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setId_fitur(String id_fitur) {
        this.id_fitur = id_fitur;
    }

    public String getId_fitur() {
        return id_fitur;
    }

    public void setNama_merchant(String nama_merchant) {
        this.nama_merchant = nama_merchant;
    }

    public String getNama_merchant() {
        return nama_merchant;
    }

    public void setAlamat_merchant(String alamat_merchant) {
        this.alamat_merchant = alamat_merchant;
    }

    public String getAlamat_merchant() {
        return alamat_merchant;
    }

    public void setLatitude_merchant(String latitude_merchant) {
        this.latitude_merchant = latitude_merchant;
    }

    public String getLatitude_merchant() {
        return latitude_merchant;
    }

    public void setLongitude_merchant(String longitude_merchant) {
        this.longitude_merchant = longitude_merchant;
    }

    public String getLongitude_merchant() {
        return longitude_merchant;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_tutup(String jam_tutup) {
        this.jam_tutup = jam_tutup;
    }

    public String getJam_tutup() {
        return jam_tutup;
    }

    public void setCategory_merchant(String category_merchant) {
        this.category_merchant = category_merchant;
    }

    public String getCategory_merchant() {
        return category_merchant;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto_ktp(String foto_ktp) {
        this.foto_ktp = foto_ktp;
    }

    public String getFoto_ktp() {
        return foto_ktp;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getChecked() {
        return checked;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public void setReferal_code(String referal_code) {
        this.referal_code = referal_code;
    }
}
