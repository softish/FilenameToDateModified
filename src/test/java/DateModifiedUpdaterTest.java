import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class DateModifiedUpdaterTest {

    @Test
    public void parseDatePortionFromFileName() {
        // given
        String fileName = "reliccoh-20091012-1648557.jpg";
        // when
        String parsedDate = DateModifiedUpdater.parseDate(fileName);
        // then
        assertEquals("20091012-164855", parsedDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseDatePortionFromFileName_fileNameWithoutDate() {
        // given
        String fileName = "wgscreenshots.db";
        // when
        DateModifiedUpdater.parseDate(fileName);
    }

    @Test
    public void parseDatePortion() {
        // given
        String dateFromFileName = "20091012-164855";
        GregorianCalendar expectedDate = new GregorianCalendar(2009, Calendar.OCTOBER, 12, 16, 48, 55);
        // when
        Date actualDate = DateModifiedUpdater.createDate(dateFromFileName);
        // then
        assertEquals(expectedDate.getTimeInMillis(), actualDate.getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsupportedDateFormat() {
        // given
        // HHmmss-yyMMdd
        String dateFromFileName = "164855-091012";
        Date expectedDate = new Date();
        // when
        DateModifiedUpdater.createDate(dateFromFileName);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testUpdateDateModifiedOfFile() {
        // given
        File fileMock = Mockito.mock(File.class);
        Mockito.when(fileMock.getName()).thenReturn("reliccoh-20091012-1648557.jpg");
        GregorianCalendar expectedTimeStamp = new GregorianCalendar(2009, Calendar.OCTOBER, 12, 16, 48, 55);
        // when
        DateModifiedUpdater.updateDateModifiedOfFile(fileMock);
        // then
        Mockito.verify(fileMock, Mockito.times(1)).setLastModified(expectedTimeStamp.getTimeInMillis());
    }
}