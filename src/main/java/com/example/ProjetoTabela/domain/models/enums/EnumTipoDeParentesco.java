package com.example.ProjetoTabela.domain.models.enums;

public enum EnumTipoDeParentesco {
    MAE{
        @Override
        public String toString(){
            return "Mãe";
        }
    },
    CONJUGE{
        @Override
        public String toString(){
            return "Conjugê";
        }
    },
    FILHO{
        @Override
        public String toString(){
            return "Filho(a)";
        }
    },
    PAI{
        @Override
        public String toString(){
            return "Pai";
        }
    },
    OUTROS{
        @Override
        public String toString(){
            return "Outros";
        }
    };

}
