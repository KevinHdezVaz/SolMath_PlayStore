package com.vazk.calculator.adaptadores;

public class GridView_adapter {



        private int imgFoto;
        public String titulo;

        public GridView_adapter(int imgFoto, String titulo) {
            this.imgFoto = imgFoto;
            this.titulo = titulo;
        }


        public int getImgFoto() {
            return imgFoto;
        }

        public void setImgFoto(int imgFoto) {
            this.imgFoto = imgFoto;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

}
