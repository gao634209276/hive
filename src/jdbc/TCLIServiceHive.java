package jdbc;

import java.util.List;

import org.apache.hive.service.cli.thrift.TCLIService;
import org.apache.hive.service.cli.thrift.TCloseOperationReq;
import org.apache.hive.service.cli.thrift.TCloseSessionReq;
import org.apache.hive.service.cli.thrift.TExecuteStatementReq;
import org.apache.hive.service.cli.thrift.TExecuteStatementResp;
import org.apache.hive.service.cli.thrift.TFetchOrientation;
import org.apache.hive.service.cli.thrift.TFetchResultsReq;
import org.apache.hive.service.cli.thrift.TFetchResultsResp;
import org.apache.hive.service.cli.thrift.TOpenSessionReq;
import org.apache.hive.service.cli.thrift.TOpenSessionResp;
import org.apache.hive.service.cli.thrift.TOperationHandle;
import org.apache.hive.service.cli.thrift.TRow;
import org.apache.hive.service.cli.thrift.TRowSet;
import org.apache.hive.service.cli.thrift.TSessionHandle;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class TCLIServiceHive {
	public static void main(String[] args) {
		TSocket transport = new TSocket("hive.example.com", 10002);

		transport.setTimeout(999999999);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TCLIService.Client client = new TCLIService.Client(protocol);
		try {
			transport.open();
			TOpenSessionReq openReq = new TOpenSessionReq();
			TOpenSessionResp openResp = client.OpenSession(openReq);
			TSessionHandle sessHandle = openResp.getSessionHandle();

			TExecuteStatementReq execReq = new TExecuteStatementReq(sessHandle, "SHOW TABLES");
			TExecuteStatementResp execResp = client.ExecuteStatement(execReq);
			TOperationHandle stmtHandle = execResp.getOperationHandle();

			TFetchResultsReq fetchReq = new TFetchResultsReq(stmtHandle, TFetchOrientation.FETCH_FIRST, 1);
			TFetchResultsResp resultsResp = client.FetchResults(fetchReq);

			TRowSet resultsSet = resultsResp.getResults();
			List<TRow> resultRows = resultsSet.getRows();
			for (TRow resultRow : resultRows) {
				resultRow.toString();
			}

			TCloseOperationReq closeReq = new TCloseOperationReq();
			closeReq.setOperationHandle(stmtHandle);
			client.CloseOperation(closeReq);
			TCloseSessionReq closeConnectionReq = new TCloseSessionReq(sessHandle);
			client.CloseSession(closeConnectionReq);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		transport.close();
	}
}
