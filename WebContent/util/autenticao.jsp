<jsp:directive.page import="java.sql.*"/>
<jsp:declaration>

	static String uri="jdbc:mysql://localhost:3306/curso_java";
	static String usuario="root";
	static String senha="senha";
	static Connection conexao;
	 
	public void init() throws ServletException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
	        System.err.println("driver não encontrado");
	       
	        
		}
		try {
			conexao= DriverManager.getConnection(uri,usuario,senha);
		} catch (SQLException e) {
			System.err.println("Não consegue conectar");
			e.printStackTrace();
		}
	}

</jsp:declaration>

<jsp:scriptlet>

String cpf=request.getParameter("cpf");
String senha=request.getParameter("senha");
String consulta="select * from login where cpf='"+cpf+"' and senha='"+senha+"'";
Statement statement;
try {
    statement =conexao.createStatement();
	ResultSet rs= statement.executeQuery(consulta);
	if(rs.next()) {
	 session.setAttribute("login","true");
	 session.setAttribute("mensagem","Usuário  autenticado!!!");
	 response.sendRedirect("http://localhost:8080/Venda-de-cursos/index.jsp");
	}
	else {
		 session.setAttribute("mensagem","Usuário não autenticado!!!");
		 response.sendRedirect("http://localhost:8080/Venda-de-cursos/login.jsp");
	}
	response.setStatus(response.SC_OK);
} catch (SQLException e) {
	
	//response.sendError(response.SC_INTERNAL_SERVER_ERROR,
			//"erro na consulta");
	out.println("consulta falhou");
	e.printStackTrace();
} 

</jsp:scriptlet>