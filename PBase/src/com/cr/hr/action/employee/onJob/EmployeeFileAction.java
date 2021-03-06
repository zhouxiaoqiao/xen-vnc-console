/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cr.hr.action.employee.onJob;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.moon.common.db.SQLTool;
import org.moon.common.util.ChinaTransCode;
import org.moon.s2sh.action.util.BaseAction;
import org.moon.s2sh.service.GeneralService;

import com.cr.util.KeyUtil;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午7:07:56 | 创建
 */
public class EmployeeFileAction extends BaseAction
{

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private GeneralService ds = new GeneralService();

	private int currPage;

	private int pageSize;

	private String sortname;

	private String sortorder;

	private String eid;

	private String job_name;

	private String age;

	private String status;

	private String college;

	private String graduate_time;

	private String join_time;

	private Integer dept_id;

	private Integer salary_month;

	private String user_account;

	private String staff_name;

	private String work_history;

	private String professional;

	private String work_year;

	private String address;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public String add() throws Throwable
	{
		logger.info("add");
		HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();
		try
		{
			setPageParm(request);

			long id = KeyUtil.getLongID();
			String sql = "INSERT  INTO tab_employee (eid,staff_name,user_account,job_name,age,status,join_time,college,graduate_time,dept_id,salary_month) VALUES ('"
					+ id
					+ "','"
					+ staff_name
					+ "','"
					+ user_account
					+ "','"
					+ job_name
					+ "','"
					+ age
					+ "','"
					+ status
					+ "','"
					+ join_time
					+ "','"
					+ college
					+ "','"
					+ graduate_time
					+ "'," + dept_id + "," + salary_month + ")";

			if (ds.insert(sql, null) > 0)
			{
				logger.info("插入成功！");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String update()
	{
		logger.info("update");
		HttpServletRequest request = ServletActionContext.getRequest();
		try
		{
			setPageParm(request);
			String u_sql = null;
			u_sql = SQLTool.appendUpdateSql(u_sql, "staff_name", "String",
					staff_name);
			u_sql = SQLTool.appendUpdateSql(u_sql, "user_account", "String",
					user_account);
			u_sql = SQLTool.appendUpdateSql(u_sql, "job_name", "String",
					job_name);
			u_sql = SQLTool.appendUpdateSql(u_sql, "age", "String",
					age);
			u_sql = SQLTool.appendUpdateSql(u_sql, "status", "String",
					status);
			u_sql = SQLTool.appendUpdateSql(u_sql, "graduate_time", "date",
					graduate_time);
			u_sql = SQLTool.appendUpdateSql(u_sql, "college", "String",
					college);
			u_sql = SQLTool.appendUpdateSql(u_sql, "dept_id", "String",
					""+dept_id);
			u_sql = SQLTool.appendUpdateSql(u_sql, "salary_month", "String",
					""+salary_month);
			u_sql = SQLTool.appendUpdateSql(u_sql, "join_time", "date",
					join_time);
			String sql = "update tab_employee set "+u_sql+" where eid=" + eid;
			if (ds.update(sql, null) > 0)
			{
				logger.info("更新成功！");
			}
		} catch (Exception e)
		{
			
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void delete()
	{
		JSONObject msg = new JSONObject();
		logger.info("delete");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try
		{
			setPageParm(request);
			String did = request.getParameter("deletes");

			if (did != null)
			{
				did = did.replaceAll(",", "','");
				String sql = "DELETE FROM  tab_employee where eid in ('" + did
						+ "')";
				int rs = ds.delete(sql, null);
				if (rs > 0)
				{
					msg.put("success", "删除" + rs + "条数据 成功！");
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
			//return "error";
		} finally
		{
			this.doJsonResponse(response, msg);
		}
		
		//return "success";
	}

	public void editData()
	{
		logger.info("editData");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String keyID = request.getParameter("keyID");
		JSONObject jsonObj = null;

		try
		{
			logger.info("keyID==" + keyID);
			String sql = "SELECT u.*,s.name as  dept_name from tab_employee u,sec_org s where  u.eid="
					+ keyID + "  and u.dept_id=s.id   ";
			List<JSONObject> jl = ds.query(sql, null);
			// JSONObject data_ret = new JSONObject();
			jsonObj = jl.get(0);

		} catch (Exception e)
		{
			jsonObj.put("error", "查询失败！");
			logger.error(e);
			//return "error";
		} finally
		{
			this.doJsonResponse(response, jsonObj);
		}
		//return "edit";
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-6-18 下午7:06:56
	 * @version 0.1
	 */
	public void initPage()
	{
		logger.info("initPage");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		JSONObject jsonObj = new JSONObject();
		String sql = null;
		try
		{
			setPageParm(request);
			if (dept_id == null)
			{
				dept_id = 200;
			}
			sql = request.getParameter("sql");
			if (sql == null || "".equals(sql) || "null".equals(sql))
				sql = "SELECT e.*,s.name as  dept_name from tab_employee e,sec_org s where e.dept_id  in (select id  from sec_org g where FIND_IN_SET(g.id, getChildLst("
						+ dept_id + ")))  and e.dept_id=s.id   ";
			if (sortname != null && !"".equals(sortname))
			{
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, sortname,
						sortorder);
			} else
				jsonObj = ds.getPageQuery(sql, currPage, pageSize, "eid",
						"desc");
			jsonObj.put("success", "查询成功！");

		} catch (Exception e)
		{
			jsonObj.put("error", "查询失败！");
			logger.error(e);
		} finally
		{
			this.doJsonResponse(response, jsonObj);
		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 周小桥 |2014-6-27 下午5:15:07
	 * @version 0.1
	 */
	public String doFind()
	{
		logger.info("doFind");
		HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();
		String sql = "SELECT * from tab_employee ";
		setPageParm(request);
		if (request.getParameter("where") != null)
		{
			String whereSQL_print = request.getParameter("where");
			whereSQL_print = ChinaTransCode.getJspUTFSubmmit(whereSQL_print);
			sql = sql + whereSQL_print;
			// 转成打印传出条件语句
			whereSQL_print = whereSQL_print.replaceAll("=", ":");
			request.setAttribute("whereSQL_print", whereSQL_print);
		}
		request.setAttribute("sql", sql);

		return "success";
	}

	
	/**
	 * @param response
	 * @param JSONObj
	 * @author 周小桥 |2014-6-26 下午5:42:30
	 * @version 0.1
	 */
	private void doJsonResponse(HttpServletResponse response, JSONObject JSONObj)
	{
		// 设置字符编码
		response.setCharacterEncoding("UTF-8");
		// 返回json对象（通过PrintWriter输出）
		try
		{
			String key = "RESPCODE";
			if (!JSONObj.containsKey(key))
			{
				JSONObj.put(key, "0000");
			}

			String resp = (String) JSONObj.get(key);

			key = "RESPMSG";
			if (!"0000".equals(resp) && !JSONObj.containsKey(key))
			{

				JSONObj.put(key, "操作错误");
			}

			response.getWriter().print(JSONObj);
		} catch (IOException e)
		{

			logger.error("写JSON返回数据出错.");
			logger.error(e);
		}
	}

	/**
	 * @param request
	 * @author 周小桥 |2014-8-18 上午10:35:45
	 * @version 0.1
	 */
	private void setPageParm(HttpServletRequest request)
	{

		currPage = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		pageSize = request.getParameter("rows") != null ? Integer
				.parseInt(request.getParameter("rows")) : 1;
		sortname = request.getParameter("sidx");
		sortorder = request.getParameter("sord");
		request.setAttribute("page", currPage);
		request.setAttribute("rows", pageSize);
		request.setAttribute("sidx", sortname);
		request.setAttribute("sord", sortorder);
	}

	public Logger getLogger()
	{
		return logger;
	}

	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}

	public String getJob_name()
	{
		return job_name;
	}

	public void setJob_name(String job_name)
	{
		this.job_name = job_name;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCollege()
	{
		return college;
	}

	public void setCollege(String college)
	{
		this.college = college;
	}

	public String getGraduate_time()
	{
		return graduate_time;
	}

	public void setGraduate_time(String graduate_time)
	{
		this.graduate_time = graduate_time;
	}

	public String getJoin_time()
	{
		return join_time;
	}

	public void setJoin_time(String join_time)
	{
		this.join_time = join_time;
	}

	public Integer getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(Integer dept_id)
	{
		this.dept_id = dept_id;
	}

	public Integer getSalary_month()
	{
		return salary_month;
	}

	public void setSalary_month(Integer salary_month)
	{
		this.salary_month = salary_month;
	}

	public String getUser_account()
	{
		return user_account;
	}

	public void setUser_account(String user_account)
	{
		this.user_account = user_account;
	}

	public String getStaff_name()
	{
		return staff_name;
	}

	public void setStaff_name(String staff_name)
	{
		this.staff_name = staff_name;
	}

	public String getWork_history()
	{
		return work_history;
	}

	public void setWork_history(String work_history)
	{
		this.work_history = work_history;
	}

	public String getProfessional()
	{
		return professional;
	}

	public void setProfessional(String professional)
	{
		this.professional = professional;
	}

	public String getWork_year()
	{
		return work_year;
	}

	public void setWork_year(String work_year)
	{
		this.work_year = work_year;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

}