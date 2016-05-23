/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import modele.Client;
import modele.Commande;
import modele.Livreur;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;
import service.ServiceMetier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author tthibault
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {   
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) 
        {
            System.out.println("Entrée Try");
            HttpSession session = request.getSession(true); //Session   
            ServiceMetier sm = new ServiceMetier();
            MySerialiser ms = new MySerialiser();
            
            String todo = request.getParameter("todo");
            
            if(todo.equals("connexion")) //si demande de connexion
            {
                Action action = new ConnexionAction();
                action.setServiceMetier(sm);
                action.execute(request);
 
                Client client = (Client) request.getAttribute("client");
                System.out.println(client);
                if(client != null)
                {
                    session.setAttribute("id", client.getId());
                    ms.printState(out,true);
                }
                else
                {
                    ms.printState(out,false);
                }
            }
            else if(todo.equals("mdpOublie"))
            {
                Action action = new MdpOublieAction();
                action.setServiceMetier(sm);
                action.execute(request);
            }
            else if(todo.equals("inscription"))
            {
                Action action = new InscriptionAction();
                action.setServiceMetier(sm);
                action.execute(request);
                   
                Client client = (Client) request.getAttribute("client");
                if(client != null)
                {
                    ms.printState(out,true);
                }
                else
                {
                    ms.printState(out,false);
                }
            }
            else
            {
                // Verif de la connexion
                Long sessionId = (Long) session.getAttribute("id");
                System.out.println("TestSession on : "+todo+" : "+sessionId);
                
                if(sessionId == null)
                {
                    //retourne vers une page de connexion
                    System.out.println("Erreur, le client doit être connecté");
                    this.getServletContext().getRequestDispatcher("/login.html").forward(request, response);
                }
                else
                {
                    switch(todo)
                    {
                        case "listeRestaurants" : 
                        {
                            Action action = new ListeRestaurantsAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object restaurants = request.getAttribute("restaurants");
                            ms.printListRestaurants(out, (List<Restaurant>) restaurants);
                            break;
                        }
                        case "restaurant" : 
                        {
                            Action action = new FicheRestaurantAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object restaurant = request.getAttribute("restaurant");
                            ms.printFicheRestaurant(out, (Restaurant)restaurant);
                            break;
                        }
                        case "modificationInfos" :
                        {
                            Action action = new ModifInfosAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            ms.printState(out,(boolean) request.getAttribute("state"));
                            break;
                        }
                        case "getPlats" :
                        {
                            Action action = new GetPlatsAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object produits = request.getAttribute("plats");
                            ms.printPlats(out, (List<Produit>)produits);
                            break;
                        }
                        case "infosCommande" :
                        {
                            Action action = new InfosCommandeAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object commande = request.getAttribute("commande");
                            ms.printInfosCommande(out, (Commande) commande);
                            break;
                        }
                        case "validerCommande" :
                        {
                            Action action = new ValiderCommandeAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            //Object commande = request.getAttribute("commande");
                            ms.printState(out, true);
                            break;
                        }
                        case "annulerCommande" :
                        {
                            Action action = new AnnulerCommandeAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            //Object commande = request.getAttribute("commande");
                            ms.printState(out, true);
                            break;
                        }
                        case "creerCommande" :
                        {
                            Action action = new CreerCommandeAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object commande = request.getAttribute("commande");
                            ms.printCreerCommande(out, (Commande) commande);
                            break;
                        }
                        case "listeCommandesClient" : 
                        {
                            Action action = new ListCommandesClientAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object commandes = request.getAttribute("commandes");
                            ms.printListCommandes(out, (List<Commande>) commandes);
                            break;
                        }
                        case "listeCommandes" : 
                        {
                            Action action = new ListCommandesAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object commandes = request.getAttribute("commandes");
                            ms.printListCommandes(out, (List<Commande>) commandes);
                            break;
                        }
                        case "listeClients" : 
                        {
                            Action action = new ListClientsAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object clients = request.getAttribute("clients");
                            ms.printListClients(out, (List<Client>) clients);
                            break;
                        }
                        case "listeProduits" : 
                        {
                            Action action = new ListProduitsAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object produits = request.getAttribute("produits");
                            ms.printPlats(out, (List<Produit>) produits);
                            break;
                        }
                        case "listeLivreurs" : 
                        {
                            Action action = new ListLivreursAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object livreurs = request.getAttribute("livreurs");
                            ms.printListLivreurs(out, (List<Livreur>) livreurs);
                            break;
                        }
                        case "getClientConnecte" :
                         {
                            System.out.println("GetClientco : "+sessionId);
                            Action action = new ClientConnecteAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object client = request.getAttribute("client");
                            ms.printInfosClient(out, (Client) client);
                            break;
                        }
                        case "creerRestaurant" :
                         {
                            Action action = new CreerRestaurantAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object restaurant = request.getAttribute("restaurant");
                            if(restaurant != null)
                            {
                                ms.printState(out,true);
                            }
                            else
                            {
                                ms.printState(out,false);
                            }
                            break;
                        }
                        case "creerLivreur" :
                         {
                            Action action = new CreerLivreurAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object livreur = request.getAttribute("livreur");
                            if(livreur != null)
                            {
                                ms.printState(out,true);
                            }
                            else
                            {
                                ms.printState(out,false);
                            }
                            break;
                        }
                        case "creerProduit" :
                         {
                            Action action = new CreerProduitAction();
                            action.setServiceMetier(sm);
                            action.execute(request);

                            Object produit = request.getAttribute("produit");
                            if(produit != null)
                            {
                                ms.printState(out,true);
                            }
                            else
                            {
                                ms.printState(out,false);
                            }
                            break;
                        }
                        case "test" :
                        {
                            Produit p = new Produit("Medhi", "Un mec du forum", 0f, 1000f);
                            ProduitCommande pc1 = new ProduitCommande(1, p);
                            ProduitCommande pc2 = new ProduitCommande(2, p);
                            ProduitCommande[] tabCommandes = {pc1,pc2};
                            
                            Gson gson = new Gson();
                            out.print(gson.toJson(tabCommandes));
                        }
                        case "test2" :
                        {
                            out.print(sessionId);
                        }
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
