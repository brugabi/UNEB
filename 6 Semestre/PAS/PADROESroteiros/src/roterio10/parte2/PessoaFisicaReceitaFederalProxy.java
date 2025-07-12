package roterio10.parte2;

public class PessoaFisicaReceitaFederalProxy implements IReceitaFederal{
    private String cpf;
    private IReceitaFederal pessoaFisicaRF = null;

    public PessoaFisicaReceitaFederalProxy(String cpf){
        this.cpf = cpf;
    }

    private void criarPessoaFisicaReceitaFederal(){
        if (pessoaFisicaRF == null){
            this.pessoaFisicaRF = new PessoaFisicaReceitaFederal(cpf);
        }
    }

    @Override
    public String getNome() {
        this.criarPessoaFisicaReceitaFederal();
        return this.pessoaFisicaRF.getNome();
    }
    @Override
    public String getCpf() {
        this.criarPessoaFisicaReceitaFederal();
        return this.pessoaFisicaRF.getCpf();
    }
    @Override
    public int getIdade() {
        this.criarPessoaFisicaReceitaFederal();
        return this.pessoaFisicaRF.getIdade();
    }
    @Override
    public boolean isCpfAtivo() {
        this.criarPessoaFisicaReceitaFederal();
        return this.pessoaFisicaRF.isCpfAtivo();
    }
}
