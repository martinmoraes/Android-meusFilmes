package br.com.escolaarcadia.muesfilmes.modelo;

import java.util.ArrayList;

public class Filme {
    private String id;
    private String titulo;
    private String imagemUrl;
    private int ano;
    private double nota;
    private ArrayList<Genero> genero = new ArrayList<Genero>();

    public Filme() {
    }

    public Filme(String id, String titulo, String imagemUrl, int ano, double nota, ArrayList<Genero> genero) {
        this.id = id;
        this.titulo = titulo;
        this.imagemUrl = imagemUrl;
        this.ano = ano;
        this.nota = nota;
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public int getId_INT() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public int getAno() {
        return ano;
    }

    public String getAnoSTR() {
        return String.valueOf(ano);
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getNota() {
        return nota;
    }

    public String getNotaSTR() {
        return String.valueOf(nota);
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public ArrayList<Genero> getGenero() {
        return genero;
    }

    public ArrayList<String> getGeneroSTR_ArrayList() {
        ArrayList<String> generos = new ArrayList<String>();
        for (Genero umGenero : this.genero) {
            generos.add(umGenero.getDescricao());
        }
        return generos;
    }

    public String getGeneroSTR() {
        String generos = "";
        for (Genero umGenero : this.genero) {
            generos = generos.concat(umGenero.getDescricao() + ", ");
        }
        return generos;
    }

    public void setGenero(ArrayList<Genero> genero) {
        this.genero = genero;
    }

    public void setGenero(String valor) {
        String[] generosSTR = valor.split(",");
        for (String str : generosSTR) {
            this.addGenero(new Genero(str));
        }
    }

    public void addGenero(Genero genero) {
        this.genero.add(genero);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ano;
        result = prime * result + ((genero == null) ? 0 : genero.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imagemUrl == null) ? 0 : imagemUrl.hashCode());
        long temp;
        temp = Double.doubleToLongBits(nota);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Filme other = (Filme) obj;
        if (ano != other.ano)
            return false;
        if (genero == null) {
            if (other.genero != null)
                return false;
        } else if (!genero.equals(other.genero))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imagemUrl == null) {
            if (other.imagemUrl != null)
                return false;
        } else if (!imagemUrl.equals(other.imagemUrl))
            return false;
        if (Double.doubleToLongBits(nota) != Double.doubleToLongBits(other.nota))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Filme [id=" + id + ", titulo=" + titulo + ", imagemUrl=" + imagemUrl + ", ano=" + ano + ", nota="
                + nota + ", genero=" + genero + "]";
    }


}
