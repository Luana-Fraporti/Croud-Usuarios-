package com.example.ProjetoTabela.domain.models.enums;

public enum EnumTipoDocumento {
    RG{
        @Override
        public String toString(){
            return "RG";
        }
    },
    CPF{
        @Override
        public String toString(){
            return "CPF";
        }
    },
    CNH{
        @Override
        public String toString(){
            return "CNH";
        }
    },
    CERTIDAO_DE_CASAMENTO{
        @Override
        public String toString(){
            return "Certidão de casamento";
        }
    },
    CERTIDAO_DE_NASCIMENTO{
        @Override
        public String toString(){
            return "Certidão de nascimento";
        }
    };

}
