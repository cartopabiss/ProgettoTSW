package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.AbbinamentoBean;
import model.AdminBean;
import model.ProdottoBean;
import dao.AbbinamentoDaoImpl;
import dao.AbbinamentoDao;

@WebServlet("/admin")
@MultipartConfig
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public AdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String azione = request.getParameter("azione");

        try {
            ProdottoDao dao = new ProdottoDaoImpl();
            AbbinamentoDao daoAbb = new AbbinamentoDaoImpl();

            if ("nuovo".equals(azione)) {
                request.setAttribute("prodotto", new ProdottoBean());
                request.setAttribute("formAction", "aggiungi");
                request.getRequestDispatcher("/WEB-INF/view/adminForm.jsp").forward(request, response);
                return;
            }

            if ("modifica".equals(azione)) {

                String idString = request.getParameter("id");

                if (idString == null || idString.isBlank()) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                int id = Integer.parseInt(idString);
                ProdottoBean prodotto = dao.doRetrieveByKey(id);

                if (prodotto == null) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }
                
                Collection<ProdottoBean> disponibili = dao.doRetrieveDisponibili(id);
                Collection<AbbinamentoBean> abbinati = daoAbb.doRetrieveByProdotto(id);
                Collection<ProdottoBean> nonAbbinati = daoAbb.doRetrieveNonAbbinati(id);

                request.setAttribute("prodotto", prodotto);
                request.setAttribute("formAction", "modifica");
                request.setAttribute("disponibili", disponibili);
                request.setAttribute("abbinati", abbinati);
                request.setAttribute("nonAbbinati", nonAbbinati);
                

                if ("BUNDLE".equals(prodotto.getCategoria())) {

                    request.setAttribute( "componenti", dao.doRetrieveProdottiBundle(id));

                }

                request.getRequestDispatcher("/WEB-INF/view/adminForm.jsp").forward(request, response);
                return;
            }
            
            // se non c'è l'azione da svolgere specificata nella get
            Collection<ProdottoBean> prodotti = dao.doRetrieveAll();
            request.setAttribute("prodotti", prodotti);

            request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException("Errore nel pannello admin", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String azione = request.getParameter("azione");

        if (azione == null || azione.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            ProdottoDao dao = new ProdottoDaoImpl();

            switch (azione) {

                case "aggiungi": {

                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String prezzoString = request.getParameter("prezzo");
                    String categoria = request.getParameter("categoria");
                    Part filePart = request.getPart("immagine");
                    String immagine = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String quantitaString = request.getParameter("quantitaMagazzino");

                    if (nome == null || nome.isBlank() ||
                        prezzoString == null || prezzoString.isBlank() ||
                        categoria == null || categoria.isBlank() ||
                        immagine == null || immagine.isBlank() ||
                        quantitaString == null || quantitaString.isBlank()) { 	
	                        response.sendRedirect(request.getContextPath() + "/admin?azione=nuovo");
	                        return;
                    }

                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setNome(nome.trim());
                    prodotto.setDescrizione(descrizione);
                    prodotto.setPrezzo(Double.parseDouble(prezzoString));
                    prodotto.setCategoria(categoria);
                    
                    String uploadPath = getServletContext().getRealPath("/images");
                    System.out.println(uploadPath);
                    filePart.write( uploadPath + File.separator + immagine );
                    File file = new File(uploadPath, immagine);

                    System.out.println("Esiste? " + file.exists());
                    System.out.println(file.getAbsolutePath());
                    
                    prodotto.setImmagine(immagine);
                    prodotto.setQuantitaMagazzino(Integer.parseInt(quantitaString));
                    prodotto.setAttivo(true);

                    dao.doSave(prodotto);
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                case "modifica": {

                    String idString = request.getParameter("id");
                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String prezzoString = request.getParameter("prezzo");
                    String categoria = request.getParameter("categoria");
                    Part filePart = request.getPart("immagine");
                    String immagine = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String quantitaString = request.getParameter("quantitaMagazzino");

                    if (idString == null || idString.isBlank() ||
                        nome == null || nome.isBlank() ||
                        prezzoString == null || prezzoString.isBlank() ||
                        categoria == null || categoria.isBlank() ||
                        //immagine == null || immagine.isBlank() ||
                        quantitaString == null || quantitaString.isBlank()) {
                    	response.sendRedirect(request.getContextPath() + "/admin?azione=modifica&id=" + idString);
	                    return;
                    }

                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setIdProdotto(Integer.parseInt(idString));
                    prodotto.setNome(nome.trim());
                    prodotto.setDescrizione(descrizione);
                    prodotto.setPrezzo(Double.parseDouble(prezzoString));
                    prodotto.setCategoria(categoria);
                    
                    ProdottoBean vecchio = dao.doRetrieveByKey(Integer.parseInt(idString));

                    if (filePart.getSize() > 0) {

                        String uploadPath = getServletContext().getRealPath("/images");

                        // elimina la vecchia immagine 
                        if (vecchio.getImmagine() != null && !vecchio.getImmagine().isBlank()) {

                            File fileVecchio = new File(uploadPath, vecchio.getImmagine());

                            if (fileVecchio.exists()) {
                                fileVecchio.delete();
                            }
                        }

                        // salva la nuova imm
                        filePart.write(uploadPath + File.separator + immagine);

                        prodotto.setImmagine(immagine);

                    } else {

                        // mantieni quella precedente
                        prodotto.setImmagine(vecchio.getImmagine());
                    }
                    
                     
                    
                    prodotto.setQuantitaMagazzino(Integer.parseInt(quantitaString));

                    dao.doUpdate(prodotto);
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }

                case "elimina": {

                    String idString = request.getParameter("id");

                    if (idString == null || idString.isBlank()) {
                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    dao.doDelete(Integer.parseInt(idString));
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }
                case "aggiungiBundle": {

                	String idBundleString = request.getParameter("idBundle");
                    String idProdottoString = request.getParameter("idProdotto");
                    String quantitaString = request.getParameter("quantita");

                    if (idBundleString == null || idProdottoString == null || quantitaString == null ||
                        idBundleString.isBlank() || idProdottoString.isBlank() || quantitaString.isBlank()) {

	                        response.sendRedirect(request.getContextPath() + "/admin");
	                        return;
                    }

                    int idBundle = Integer.parseInt(idBundleString);
                    int idProdotto = Integer.parseInt(idProdottoString);
                    int quantita = Integer.parseInt(quantitaString);

                    dao.doAddProdottoBundle(idBundle, idProdotto, quantita);

                    response.sendRedirect(request.getContextPath() + "/admin?azione=modifica&id=" + idBundle);
                    return;
                }
                case "rimuoviBundle": {

                    String idBundleString = request.getParameter("idBundle");
                    String idProdottoString = request.getParameter("idProdotto");

                    if (idBundleString == null || idProdottoString == null ||
                        idBundleString.isBlank() || idProdottoString.isBlank()) {

                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    int idBundle = Integer.parseInt(idBundleString);
                    int idProdotto = Integer.parseInt(idProdottoString);

                    dao.doRemoveProdottoBundle(idBundle, idProdotto);

                    response.sendRedirect(request.getContextPath() + "/admin?azione=modifica&id=" + idBundle);
                    return;
                }
                case "aggiungiAbbinamento": {

                    String id1String = request.getParameter("idProdotto1");
                    String id2String = request.getParameter("idProdotto2");

                    if (id1String == null || id2String == null ||
                        id1String.isBlank() || id2String.isBlank()) {

                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    int id1 = Integer.parseInt(id1String);
                    int id2 = Integer.parseInt(id2String);

                    if (id1 == id2) {
                        response.sendRedirect(request.getContextPath() + "/admin?azione=modifica&id=" + id1);
                        return;
                    }

                    AbbinamentoDao daoA = new AbbinamentoDaoImpl();
                    daoA.doSave(id1, id2);

                    response.sendRedirect(request.getContextPath() + "/admin?azione=modifica&id=" + id1);
                    return;
                }
                case "rimuoviAbbinamento": {

                    String id1String = request.getParameter("idProdotto1");
                    String id2String = request.getParameter("idProdotto2");
                    String ritornaAString = request.getParameter("ritornaA");

                    if (id1String == null || id2String == null || ritornaAString == null ||
                        id1String.isBlank() || id2String.isBlank() || ritornaAString.isBlank() ) {

                        response.sendRedirect(request.getContextPath() + "/admin");
                        return;
                    }

                    int id1 = Integer.parseInt(id1String);
                    int id2 = Integer.parseInt(id2String);
                    int ritornaA = Integer.parseInt(ritornaAString);
                    
                    System.out.print("ritorna a: " + ritornaA);

                    

                    try {
                        AbbinamentoDao daoA = new AbbinamentoDaoImpl();
                        daoA.doDelete(id1, id2);

                    } catch (SQLException e) {
                        throw new ServletException("Errore eliminazione abbinamento", e);
                    }

                    // ritorno alla pagina modifica prodotto
                    response.sendRedirect( request.getContextPath() + "/admin?azione=modifica&id=" + ritornaA);
                    return;
                }

                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (SQLException e) {
            throw new ServletException("Errore durante la gestione admin", e);
        }
    }
}