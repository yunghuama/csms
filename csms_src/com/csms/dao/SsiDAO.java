package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.Ssi;
import com.csms.domain.Warn;
import com.platform.dao.GenericDAO;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       ContentDAO.java</p>
 * <p>程序说明：       短信接口DAO</p>
 * <p>时间：          2013-1-14</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class SsiDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static SsiDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static SsiDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new SsiDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public SsiDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}


    /**
     * 保存报警信息
     * @param cpu
     * @param memusage
     * @param storage
     * @return
     */
    public int saveSsiRep(Ssi ssi){
       return jdbcTemplate.update(CSMSSQLConstant.SSI_RSP_SAVE,new Object[]{
          ssi.getServiceFlag(),
          ssi.getDestAddressTon(),
          ssi.getDestAddressNpi(),
          ssi.getDestinationAddress(),
          "",
          "0",
          ssi.getShortMessage(),
          ssi.getMsgtype(),
          ssi.getState(),
          new Date()
        });
    }


    /**
     * 查询待发送的短信
     * @param page
     * @return
     */
    public List<Ssi> listSsi(Object[] args){
//    	Object[] preparedArgs = Meta.addPageToParameters(args, page);
    	return jdbcTemplate.query(CSMSSQLConstant.SSI_SELECT_BY_INCREASE_ID,args, new RowMapper<Ssi>() {
			@Override
			public Ssi mapRow(ResultSet rs, int position) throws SQLException {
				Ssi ssi = new Ssi();
				ssi.setCsmClass(rs.getInt("esm_class"));
				ssi.setDataCoding(rs.getInt("data_coding"));
				ssi.setDestAddressNpi(rs.getInt("dest_address_npi"));
				ssi.setDestAddressTon(rs.getInt("dest_address_ton"));
				ssi.setDestinationAddress(rs.getString("destination_address"));
				ssi.setMsgtype(rs.getInt("msgtype"));
				ssi.setNetid(rs.getInt("netid"));
				ssi.setPriorityFlag(rs.getInt("priority_flag"));
				ssi.setProtocolId(rs.getInt("protocol_id"));
				ssi.setRegisteredDeliveryFlag(rs.getInt("registered_delivery_flag"));
				ssi.setReplaceIfPresentFlag(rs.getInt("replace_if_present_flag"));
				ssi.setSarMsgRefNumV(rs.getInt("sar_msg_ref_numV"));
				ssi.setSarSegmentSeqnumV(rs.getInt("sar_segment_seqnumV"));
				ssi.setSarTotalSegmentsV(rs.getInt("sar_total_segmentsV"));
				ssi.setScheduleDeliverytime(rs.getString("schedule_delivery_time"));
				ssi.setSequenceNumber(rs.getInt("sequence_number"));
				ssi.setServiceFlag(rs.getInt("service_flag"));
				ssi.setServiceType(rs.getString("service_type"));
				ssi.setShortMessage(rs.getString("short_message"));
				ssi.setSid(rs.getInt("sid"));
				ssi.setSmDefaultMsgId(rs.getInt("sm_default_msg_id"));
				ssi.setSmLength(rs.getInt("sm_length"));
				ssi.setSourceAddress(rs.getString("source_address"));
				ssi.setSourceAddressNpi(rs.getInt("source_address_npi"));
				ssi.setSourceAddressTon(rs.getInt("source_address_ton"));
				ssi.setSsiparty(rs.getInt("ssiparty"));
				ssi.setTimeStamp(rs.getString("time_stamp"));
				ssi.setState(rs.getInt("state"));
				ssi.setValidityPeroid(rs.getString("validity_peroid"));
				return ssi;
			}
		});
 
    }
}