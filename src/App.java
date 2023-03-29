import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        String s;
        int rating;
        int i;
        double ratingFloat;
        // Fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();


        // Pegar só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);


        // Exibir e manipular os dados
        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
        for (Map<String,String> filme: listaDeFilmes) {

            String urlImage = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImage).openStream();
            String nomeArquivo = titulo + ".png";


            geradora.cria(inputStream, nomeArquivo);


            System.out.println();
            System.out.println("Título: " + filme.get("title"));

            s = filme.get("imDbRating");
            ratingFloat = Double.parseDouble(s);
            rating = (int) Math.floor(ratingFloat);
            System.out.println("Classificação: " + s);
            for(i=0; i<rating ; i++){
                System.out.printf("⭐");
            }




            System.out.println();
            System.out.println();
        }
    }
}