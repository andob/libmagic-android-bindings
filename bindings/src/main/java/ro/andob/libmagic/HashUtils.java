package ro.andob.libmagic;

import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import kotlin.jvm.functions.Function0;

class HashUtils
{
    @SuppressWarnings("StatementWithEmptyBody")
    public static @Nullable String md5Checksum(File file)
    {
        try
        {
            if (!file.exists())
                throw new RuntimeException("File does not exist: "+file.getAbsolutePath());

            MessageDigest digest=MessageDigest.getInstance("MD5");

            byte[] buffer=new byte[1024*1024];
            try (DigestInputStream inputStream=new DigestInputStream(new FileInputStream(file), digest))
            {
                //read all stream, until EOF
                while(inputStream.read(buffer)>0) {}
            }

            byte[] messageDigest=digest.digest();
            return hexString(messageDigest);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static @Nullable String md5Checksum(Function0<InputStream> inputStreamProvider)
    {
        try
        {
            MessageDigest digest=MessageDigest.getInstance("MD5");

            byte[] buffer=new byte[1024*1024];
            try (DigestInputStream inputStream=new DigestInputStream(inputStreamProvider.invoke(), digest))
            {
                //read all stream, until EOF
                while(inputStream.read(buffer)>0) {}
            }

            byte[] messageDigest=digest.digest();
            return hexString(messageDigest);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public static String hexString(byte[] bytes)
    {
        StringBuilder hexString=new StringBuilder();
        for (byte aByte : bytes)
        {
            String h=Integer.toHexString(0xFF&aByte);
            while (h.length()<2)
                h="0"+h;
            hexString.append(h);
        }

        return hexString.toString();
    }
}
