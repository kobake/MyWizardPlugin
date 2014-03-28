package mywizardplugin.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/*
org.eclipse.ui.dialogs.WizardNewFileCreationPage	ファイルを作成
org.eclipse.ui.dialogWizardNewFolderMainPages		フォルダを作成
org.eclipse.ui.dialogs.WizardNewProjectCreationPage	プロジェクトを作成
org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPage	Javaプロジェクトを作成
org.eclipse.jdt.ui.wizards.NewPackageWizardPage		Javaパッケージを作成
org.eclipse.jdt.ui.wizards.NewClassWizardPage		Java クラスを作成
org.eclipse.jdt.ui.wizards.NewInterfaceWizardPage	Javaインターフェイスを作成
org.eclipse.jdt.ui.wizards.NewAnnotationWizardPage	Javaアノテーションを作成
org.eclipse.jdt.ui.wizards.NewEnumWizardPage		Java列挙型を作成
 */
public class NewWizardPage extends WizardNewFileCreationPage {
	public NewWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
	}
	
	// 新規作成するファイルの内容
	protected InputStream getInitialContents(){
		try{
			// 文字コードを取得
			String projectName = getContainerFullPath().segment(0);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			String charset = project.getDefaultCharset();
			
			// 作成するXMLファイルの内容を作成
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\"");
			if(charset != null){
				sb.append(" encoding=\"" + charset + "\"");
			}
			sb.append("?>\n");
			return new ByteArrayInputStream(sb.toString().getBytes());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	// 入力値のバリデーション
	protected boolean validatePage(){
		boolean valid = super.validatePage();
		if(valid){
			// 拡張子はxmlのみ許可
			if(!getFileName().endsWith(".xml")){
				setMessage("拡張子がxmlではありません。", ERROR);
				return false;
			}
		}
		return valid;
	}
}
