package com.gtr.bmhr;

        import com.gtr.bmhr.Model.AttendanceDataShow;
        import com.gtr.bmhr.Model.AttendancePostClass;
        import com.gtr.bmhr.Model.EmployeeInfo;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface Api {
    @GET("getuser/{userName}/{userPassword}")
    Call<EmployeeInfo> getEmployeeDataSet(@Path("userName") String userName,
                                          @Path("userPassword") String userPassword);

    @POST("SetAttendance")
    Call<String> postAttendance(@Body AttendancePostClass attendancePostClass);

    @GET("getpunchdetails/{empId}/{dateTime}")
    Call<List<AttendanceDataShow>> getAttendanceDataSet(@Path("empId") Integer empId,
                                                  @Path("dateTime") String dateTime);
}

