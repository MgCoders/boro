package uy.mgcoders.boro.util;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by raul on 01/05/15.
 */
public class DateFormatTransformer implements Transform<Date> {
    private DateFormat dateFormat;


    public DateFormatTransformer(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    @Override
    public Date read(String value) throws Exception {

        return new Date(Long.parseLong(value));
    }


    @Override
    public String write(Date value) throws Exception {
        return String.valueOf(value.getTime());
    }

}
