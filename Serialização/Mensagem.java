import java.io.Serializable;

public class Mensagem implements Serializable {
   private static final long serialVersionUID = 1L;
   private String operacao;
   private String parametro;
   private String resposta;

   public Mensagem(String var1, String var2) {
      this.operacao = var1;
      this.parametro = var2;
   }

   public String getOperacao() {
      return this.operacao;
   }

   public String getParametro() {
      return this.parametro;
   }

   public void setResposta(String var1) {
      this.resposta = var1;
   }

   public String getResposta() {
      return this.resposta;
   }
}
