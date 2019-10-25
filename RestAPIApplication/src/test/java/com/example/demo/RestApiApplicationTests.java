package com.example.demo;

import java.io.File;
import java.io.FileInputStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.constants.FileManagementConstants;
import com.example.demo.controller.FileManagementController;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestApiApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;


	@Test
	public void test1uploadTestWhenKeyIsGivenMD5() throws Exception {

		File testUploadFile = new File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST);

		FileInputStream fi1 = new FileInputStream(testUploadFile);
		MockMultipartFile fstmp = new MockMultipartFile("file", testUploadFile.getName(), "multipart/form-data", fi1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(
				MockMvcRequestBuilders.multipart("http://localhost:8080/testApplicationContext/uploadData/".concat(FileManagementConstants.KEY))
						.file(fstmp).header("checksum-type", "MD5"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.header().string("checksum", "079f7410ce7a6491b8334364199ab317"));		

	}
	
	@Test
	public void test2uploadTestWhenKeyIsNotGivenMD5() throws Exception {

		File testUploadFile = new File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST);

		FileInputStream fi1 = new FileInputStream(testUploadFile);
		MockMultipartFile fstmp = new MockMultipartFile("file", testUploadFile.getName(), "multipart/form-data", fi1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(
				MockMvcRequestBuilders.multipart("http://localhost:8080/testApplicationContext/uploadData/")
						.file(fstmp).header("checksum-type", "MD5"))
				.andExpect(MockMvcResultMatchers.status().is(404));
					

	}
	@Test
	public void test3uploadTestWhenKeyIsGivenSHA256() throws Exception {

		File testUploadFile = new File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST_SHA256);

		FileInputStream fi1 = new FileInputStream(testUploadFile);
		MockMultipartFile fstmp = new MockMultipartFile("file", testUploadFile.getName(), "multipart/form-data", fi1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(
				MockMvcRequestBuilders.multipart("http://localhost:8080/testApplicationContext/uploadData/".concat(FileManagementConstants.KEY))
						.file(fstmp).header("checksum-type", "SHA-256"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.header().string("checksum", "8beb9c9db1f2844ae6b1c40706275f5bc360601d1c68a04a8afeea4c13d9e9f1"));		

	}
	@Test
	public void test4uploadTestWhenKeyIsNotGivenSHA256() throws Exception {

		File testUploadFile = new File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST_SHA256);

		FileInputStream fi1 = new FileInputStream(testUploadFile);
		MockMultipartFile fstmp = new MockMultipartFile("file", testUploadFile.getName(), "multipart/form-data", fi1);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(
				MockMvcRequestBuilders.multipart("http://localhost:8080/testApplicationContext/uploadData/")
						.file(fstmp).header("checksum-type", "SHA-256"))
				.andExpect(MockMvcResultMatchers.status().is(404));
					

	}

	@Test
	public void test5DownloadTestWhenKeyIsGiven() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/testApplicationContext/downloadData/".concat(FileManagementConstants.KEY))
				.param("fileName", "Read.txt"))

				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.header().string("checksum", "079f7410ce7a6491b8334364199ab317"));

	}
	@Test
	public void test6DownloadTestWhenKeyNotGiven() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/testApplicationContext/downloadData/")
				.param("fileName", "Read.txt"))

				.andExpect(MockMvcResultMatchers.status().is(404));				

	}

	
	@Test
	public void test7RemoveTestWhenKeyIsGiven() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/testApplicationContext/removeData/".concat(FileManagementConstants.KEY))
				.param("fileName", "Read.txt"))

				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.header().string("checksum", "d41d8cd98f00b204e9800998ecf8427e"));

	}
	@Test
	public void test8RemoveTestWhenKeyNotGiven() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/testApplicationContext/removeData/")
				.param("fileName", "Read.txt"))

				.andExpect(MockMvcResultMatchers.status().is(404));
				

	}

}

/*
 * //given InputStream uploadStream =
 * fileManagementController.getClassLoader().getResourceAsStream(
 * "exceldocument.xlsx"); //FileInputStream uploadStream = new
 * FileInputStream(testUploadFile);
 * 
 * MockMultipartFile file = new MockMultipartFile("file", uploadStream); assert
 * uploadStream != null;
 * 
 * //when this.mockMvc.perform(fileUpload("/DefectImport") .file(file)) //then
 * .andExpect(status().isOk());
 * 
 * RestAssured.given().header("checksum-type",
 * FileManagementConstants.MD5).multiPart(testUploadFile).when()
 * .post("/uploadData/" + FileManagementConstants.KEY).then().assertThat()
 * .header(FileManagementConstants.CHECKSUM_RESPONSE_HEADER,
 * "079f7410ce7a6491b8334364199ab317")
 * .header(FileManagementConstants.STATUS_RESPONSE_HEADER,
 * HttpStatus.OK.toString()).statusCode(200);
 */

/*
 * @Test public void test2uploadTestWhenKeyIsNotPresentMD5() {
 * 
 * File testUploadFile = new
 * File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST); RestAssured.baseURI =
 * "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().header("checksum-type",
 * FileManagementConstants.MD5).multiPart(testUploadFile).when()
 * .post("/uploadData").then().statusCode(404); }
 * 
 * @Test public void test3uploadTestWhenKeyIsGivenSHA256() {
 * 
 * File testUploadFile = new
 * File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST_SHA256);
 * RestAssured.baseURI = "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().header("checksum-type",
 * FileManagementConstants.SHA256).multiPart(testUploadFile).when()
 * .post("/uploadData/" + FileManagementConstants.KEY).then().assertThat()
 * .header(FileManagementConstants.CHECKSUM_RESPONSE_HEADER,
 * "8beb9c9db1f2844ae6b1c40706275f5bc360601d1c68a04a8afeea4c13d9e9f1")
 * .header(FileManagementConstants.STATUS_RESPONSE_HEADER,
 * HttpStatus.OK.toString()).statusCode(200); }
 * 
 * @Test public void test4uploadTestWhenKeyIsNotPresentSHA256() {
 * 
 * File testUploadFile = new
 * File(FileManagementConstants.LOCAL_FILE_PATH_FOR_TEST_SHA256);
 * RestAssured.baseURI = "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().header("checksum-type",
 * FileManagementConstants.SHA256).multiPart(testUploadFile).when()
 * .post("/uploadData").then().statusCode(404); }
 * 
 * @Test public void test5downloadDataWithFileKey() { RestAssured.baseURI =
 * "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().when().get("/downloadData/" + FileManagementConstants.KEY
 * + "?fileName=Read.txt").then() .assertThat()
 * .header(FileManagementConstants.CHECKSUM_RESPONSE_HEADER,
 * "079f7410ce7a6491b8334364199ab317")
 * .header(FileManagementConstants.STATUS_RESPONSE_HEADER,
 * HttpStatus.OK.toString()) .statusCode(200); }
 * 
 * @Test public void test6downloadDataWithoutKey() { RestAssured.baseURI =
 * "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().when().get("/downloadData?fileName=Read.txt").then().
 * statusCode(404); }
 * 
 * @Test public void test7removeDataWithKey() { RestAssured.baseURI =
 * "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().when().get("/removeData/" + FileManagementConstants.KEY +
 * "?fileName=Read.txt").then() .assertThat()
 * .header(FileManagementConstants.CHECKSUM_RESPONSE_HEADER,
 * "d41d8cd98f00b204e9800998ecf8427e") .statusCode(200); }
 * 
 * @Test public void test8removeDataWithoutKey() { RestAssured.baseURI =
 * "http://localhost:8080/testApplicationContext";
 * 
 * RestAssured.given().when().get("/removeData/?fileName=Read.txt").then()
 * .statusCode(404); }
 */
