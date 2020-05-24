package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abctreinamentos.Cliente;
import com.abctreinamentos.Curso;
import com.abctreinamentos.Pagamento;
import com.abctreinamentos.PagamentoId;

/**
 * Servlet implementation class Controlador
 */
@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
	EntityManager em = emf.createEntityManager();

    /**
     * Default constructor. 
     */
    public Controlador() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		int idformulario;//1-clientes, 2-cursos, 3-pagamentos
		int tipoformulario;//1.1-consulta...
	    idformulario=Integer.parseInt(request.getParameter("idformulario"));
	    tipoformulario=Integer.parseInt(request.getParameter("tipoformulario"));
	    String cpf;
	    String email,datainscricao;
	    String nome,nomecurso, site;
	    double valorcurso;
	    int cdcurso;
	    EntityTransaction tx = em.getTransaction();
	    HttpSession session=request.getSession();
	    
	    if(idformulario==1) {//clientes
	    	switch(tipoformulario) {
	    	  case 11:{
	    	    TypedQuery<Cliente> query = em.createQuery(""
							+ "Select c from Cliente c",Cliente.class);
				List<Cliente> clientes = query.getResultList(); 
				session.setAttribute("clientes", clientes);
				session.setAttribute("mensagem","Total de clientes: "+clientes.size());
				response.sendRedirect("clientes/consultaTodos2.jsp");
				break;
				}
	    	  case 12:{
	    		cpf= request.getParameter("cpf");
	    		Cliente cliente = em.find(Cliente.class,cpf);
	    		
	    		if(cliente!=null)//Cliente encontrado 
	    		{
	    			session.setAttribute("mensagem","Cliente "+cpf+" encontrado!!!");
	    			session.setAttribute("cliente", cliente);
	    		}
	    		else {
	    			session.setAttribute("mensagem","Cliente "+cpf+" não encontrado!!!");
	    			session.setAttribute("cliente", null);
	    		}
	    		response.sendRedirect("clientes/resposta.jsp");
	    		break;
	    	  }
	    	  case 13:{
	    			cpf= request.getParameter("cpf");
	    			nome= request.getParameter("nome");
	    			email= request.getParameter("email");
	    			Cliente cliente = em.find(Cliente.class,cpf);
		    		
		    		if(cliente!=null)
		    		{
		    			session.setAttribute("mensagem","Esse cliente já existe, cadastramento cancelado!!");
		    			session.setAttribute("cliente", null);
		    		}
		    	else {
		    			
		    		
	    		    cliente= new Cliente(cpf,nome,email);
	    			tx.begin();
	    			em.persist(cliente);
	    			tx.commit();
	    			session.setAttribute("mensagem","Cliente "+cpf+" cadastrado!");
	    			session.setAttribute("cliente", cliente);
	    			    		
		    	}
		    	response.sendRedirect("clientes/resposta.jsp");	
		    	break;
	    	  }
	    	  case 14:{
	    		    cpf= request.getParameter("cpf");
	    			nome= request.getParameter("nome");
	    			email= request.getParameter("email");
	    			Cliente cliente = em.find(Cliente.class,cpf);
		    		
		    		if(cliente!=null)
		    		{
		    		    cliente= new Cliente(cpf,nome,email);
		    			tx.begin();
		    			em.merge(cliente);
		    			tx.commit();
		    			session.setAttribute("mensagem","Cliente "+cpf+" alterado!");
		    			session.setAttribute("cliente", cliente);
		    		}
		    		else {
		    			session.setAttribute("mensagem","Cliente "+cpf+" não encontrado, alteração cancelada!");
		    			session.setAttribute("cliente", null);
		    		}		    	   	    		
		    		response.sendRedirect("clientes/resposta.jsp");
		    		break;
	    	  }
	    	  case 15:{	
	    		cpf= request.getParameter("cpf");
                Cliente cliente = em.find(Cliente.class,cpf);
	    		
	    		if(cliente!=null) 
	    		{
	    			tx.begin();
	    			em.remove(cliente);
	    			tx.commit();
	    			session.setAttribute("mensagem","Cliente "+cpf+" removido!");
	    			
	    		}
	    		else {
	    			session.setAttribute("mensagem","Cliente "+cpf+" não encontrado, remoção cancelada!");
	    		}
	    	    session.setAttribute("cliente", null);	    		
	    		response.sendRedirect("clientes/resposta.jsp");
    		    break;
    		  }  
	    	}
	    }
	    else if(idformulario==2) {//cursos
	    	
	    	switch(tipoformulario) {
	    	  case 21:{
					TypedQuery<Curso> query = em.createQuery(""
					+ "Select c from Curso c",Curso.class);
					List<Curso> cursos = query.getResultList();
					session.setAttribute("cursos", cursos);
					session.setAttribute("mensagem","Total de cursos: "+cursos.size());
					response.sendRedirect("cursos/consultaTodos.jsp");
	    		  break;
	    		  }
	    	  case 22:{
	    		 cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
                 Curso curso = em.find(Curso.class,cdcurso);
	    		
	    		if(curso!=null)//Curso encontrado 
	    		{
	    			session.setAttribute("mensagem","Curso "+cdcurso+" encontrado!!!");
	    			session.setAttribute("curso", curso);
	    		}
	    		else {
	    			session.setAttribute("mensagem","Curso "+cdcurso+" não encontrado!!!");
	    			session.setAttribute("curso", null);
	    		}
	    		response.sendRedirect("cursos/resposta.jsp");
	    		break;
	    	  }
	    	  case 23:{
	    		    cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    			nomecurso= request.getParameter("nome");
	    			site= request.getParameter("site");
	    			valorcurso= Double.parseDouble(request.getParameter("valor"));	
	    			Curso curso = em.find(Curso.class,cdcurso);
	    			if(curso!=null)
		    		{
		    			session.setAttribute("mensagem","Esse curso já existe, cadastramento cancelado!!");
		    			session.setAttribute("curso", null);
		    		}
		    	else {
		    			
		    		
		    		curso= new Curso(cdcurso,nomecurso,valorcurso,site);
	    			tx.begin();
	    			em.persist(curso);
	    			tx.commit();
	    			session.setAttribute("mensagem","Curso "+cdcurso+" cadastrado!");
	    			session.setAttribute("curso", curso);
	    			    		
		    	}
	    			response.sendRedirect("cursos/resposta.jsp");
		    		break;
	    	  }
	    	  case 24:{
	    		    cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    			nomecurso= request.getParameter("nome");
	    			site= request.getParameter("site");
	    			valorcurso= Double.parseDouble(request.getParameter("valor"));
	    			Curso curso = em.find(Curso.class,cdcurso);
	    			if(curso!=null)
		    		{
	    				curso= new Curso(cdcurso,nomecurso,valorcurso,site);
		    			tx.begin();
		    			em.merge(curso);
		    			tx.commit();
		    			session.setAttribute("mensagem","Curso "+cdcurso+" alterado!");
		    			session.setAttribute("curso", curso);
		    		}
		    		else {
		    			session.setAttribute("mensagem","Curso "+cdcurso+" não encontrado, alteração cancelada!");
		    			session.setAttribute("curso", null);
		    		}		    	   	    		
		    		response.sendRedirect("cursos/resposta.jsp");
		    		break;
	    	  }
	    	  case 25:{	
	    		  cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    		  Curso curso = em.find(Curso.class,cdcurso);
	    			if(curso!=null)
		    		{
		    			tx.begin();
		    			em.remove(curso);
		    			tx.commit();
		    			session.setAttribute("mensagem","Curso "+cdcurso+" removido!");
		    			
		    		}
		    		else {
		    			session.setAttribute("mensagem","Curso "+cdcurso+" não encontrado, remoção cancelada!");
		    		}
		    	    session.setAttribute("curso", null);	    		
		    		response.sendRedirect("cursos/resposta.jsp");
	    		  
  		    break;
  		  }  
	    	}
	    }
	    else if(idformulario==3) {//pagamentos
	    	switch(tipoformulario) {
	    	  case 31:{
	    		  TypedQuery<Pagamento> query = em.createQuery(""
				   + "Select p from Pagamento p",Pagamento.class);
				    List<Pagamento> pagamentos = query.getResultList();
				    session.setAttribute("mensagem","Total de pagamentos: "+ pagamentos.size());
    			    session.setAttribute("pagamentos",pagamentos);
    			    response.sendRedirect("pagamentos/consultaTodos3.jsp");
	    		  break;
	    		  }
	    	  case 32:{
	    		cpf= request.getParameter("cpf");
	    		cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    		PagamentoId pgtoid= new PagamentoId(cpf,cdcurso);
	    		Pagamento pagamento=em.find(Pagamento.class,pgtoid);;
	    		
	    		if(pagamento!=null)//Curso encontrado 
	    		{
	    			session.setAttribute("mensagem","Pagamento do "+cpf+" para o "+cdcurso+" encontrado!!!");
	    			session.setAttribute("pagamento",pagamento);
	    		}
	    		else {
	    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" não encontrado!!!");
	    			session.setAttribute("pagamento", null);
	    		}
	    		response.sendRedirect("pagamentos/resposta.jsp");
	    		break;
	    	  }
	    	  case 33:{
	    		    cpf= request.getParameter("cpf");
	    		    cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    			datainscricao= request.getParameter("datainscricao");
	    			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    			LocalDate date =LocalDate.parse(datainscricao, formatter);
	    			DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    			PagamentoId pgtoid= new PagamentoId(cpf,cdcurso);
		    		Pagamento pagamento=em.find(Pagamento.class,pgtoid);
		    		datainscricao=fmt.format(date);
		    		if(pagamento!=null)//Curso encontrado 
		    		{
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" já existe, cadastramento cancelado");
		    			session.setAttribute("pagamento",null);
		    		}
		    		else {
		    			
			    		
			    		pagamento= new Pagamento(pgtoid,datainscricao);
		    			tx.begin();
		    			em.persist(pagamento);
		    			tx.commit();
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" cadastrado");
		    			session.setAttribute("pagamento", pagamento);
		    			    		
			    	}
		    			response.sendRedirect("pagamentos/resposta.jsp");
		    		break;
	    	  }
	    	  case 34:{
	    		   cpf= request.getParameter("cpf");
	    		   cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    			datainscricao= request.getParameter("datainscricao");
	    			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    			LocalDate date =LocalDate.parse(datainscricao, formatter);
	    			DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    			datainscricao=fmt.format(date);
	    			PagamentoId pgtoid= new PagamentoId(cpf,cdcurso);
		    		Pagamento pagamento=em.find(Pagamento.class,pgtoid);
	    			if(pagamento!=null)
		    		{
	    				pagamento=  new Pagamento(pgtoid,datainscricao);
		    			tx.begin();
		    			em.merge(pagamento);
		    			tx.commit();
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" alterado!");
		    			session.setAttribute("pagamento",pagamento);
		    		}
		    		else {
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" não encontrado, alteração cancelada!");
		    			session.setAttribute("pagamento", null);
		    		}		    	   	    		
		    		response.sendRedirect("pagamentos/resposta.jsp");
		    		
		    		break;
	    	  }
	    	  case 35:{	
	    		  cpf= request.getParameter("cpf");
	    		  cdcurso= Integer.parseInt(request.getParameter("cdcurso"));
	    		  PagamentoId pgtoid= new PagamentoId(cpf,cdcurso);
		    	  Pagamento pagamento=em.find(Pagamento.class,pgtoid);
		    	  if(pagamento!=null)
		    		{
		    			tx.begin();
		    			em.remove(pagamento);
		    			tx.commit();
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" removido");
		    			}
		    	  else {
		    			session.setAttribute("mensagem","Pagamento do"+cpf+" para o "+cdcurso+" não encontrado, remoção cancelada!");
		    		}
		    	    session.setAttribute("pagamento", null);	    		
		    		response.sendRedirect("pagamentos/resposta.jsp");
		    break;
		    }  
	      }
	  }
	

	}
}