package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.process.Process;
import com.abelium.inatrace.db.entities.common.Document;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public class ProductContent extends BaseEntity {
	
	/**
	 * product name
	 */
	@Column(length = Lengths.DEFAULT)
	private String name;

	/**
	 * high-resolution photo of the product (jpeg, jpg, png), 72dpi and 1200x2000px recommended 
	 */
	@ManyToOne
	private Document photo;
	
	/**
	 * product description - briefly describe the product AND reasons why your product is worth buying 
	 * (example: special properties, health benefits, great value, organic production....)
	 */
	@Lob
	private String description;

	/**
	 * ingredients - list the ingredients in the product and describe their properties 
	 * (example: Organic Shea Butter, Fair Trade Mango....)
	 */
	@Lob
	private String ingredients;
	
	/**
	 * nutritional Value - list the nutritional value of the product
	 */
	@Lob
	private String nutritionalValue;

	/**
	 * how to Use / Recipes - Describe the best way to use the product (e.g. recipes, how to apply the product…)
	 */
	@Lob
	private String howToUse;
	
	/**
	 * origin - text and quantity input - Briefly describe where the product or its 
	 * ingredients are produced. 
	 */
	@Lob
	private String originText;
		
	/**
	 * Key Markets
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "market")
    @Column(name = "share")	
    @CollectionTable
    private Map<String, Double> keyMarketsShare = new HashMap<>();

	/**
	 * process
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private Process process = new Process();
	
	/**
	 * social responsibility
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private Responsibility responsibility = new Responsibility();
	
	/**
	 * environmental sustainability
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private Sustainability sustainability = new Sustainability();

    /**
     * journey coordinates
     */
    @OneToOne(fetch = FetchType.LAZY)
    private ProductJourney journey = new ProductJourney();

	/**
	 * speciality document
	 */
	@ManyToOne
	private Document specialityDocument;
	
	/**
	 * speciality description
	 */
	@Lob
	private String specialityDescription;
	
	/**
	 * product settings
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private ProductSettings settings = new ProductSettings();
	
	/**
	 * comparison of price
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private ComparisonOfPrice comparisonOfPrice = new ComparisonOfPrice();
	
	/**
	 * knowledge blog
	 */
	@Column
	private Boolean knowledgeBlog;

	@OneToOne
	private BusinessToCustomerSettings businessToCustomerSettings = new BusinessToCustomerSettings();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getNutritionalValue() {
		return nutritionalValue;
	}

	public void setNutritionalValue(String nutritionalValue) {
		this.nutritionalValue = nutritionalValue;
	}

	public String getHowToUse() {
		return howToUse;
	}

	public void setHowToUse(String howToUse) {
		this.howToUse = howToUse;
	}

	public String getOriginText() {
		return originText;
	}

	public void setOriginText(String originText) {
		this.originText = originText;
	}

	public Map<String, Double> getKeyMarketsShare() {
		return keyMarketsShare;
	}

	public void setKeyMarketsShare(Map<String, Double> keyMarketsShare) {
		this.keyMarketsShare = keyMarketsShare;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
	}

	public Sustainability getSustainability() {
		return sustainability;
	}

	public void setSustainability(Sustainability sustainability) {
		this.sustainability = sustainability;
	}

	public Document getSpecialityDocument() {
		return specialityDocument;
	}

	public void setSpecialityDocument(Document specialityDocument) {
		this.specialityDocument = specialityDocument;
	}

	public String getSpecialityDescription() {
		return specialityDescription;
	}

	public void setSpecialityDescription(String specialityDescription) {
		this.specialityDescription = specialityDescription;
	}

	public ProductSettings getSettings() {
		return settings;
	}

	public void setSettings(ProductSettings settings) {
		this.settings = settings;
	}

	public ComparisonOfPrice getComparisonOfPrice() {
		return comparisonOfPrice;
	}

	public void setComparisonOfPrice(ComparisonOfPrice comparisonOfPrice) {
		this.comparisonOfPrice = comparisonOfPrice;
	}

	public Boolean getKnowledgeBlog() {
		return knowledgeBlog;
	}

	public void setKnowledgeBlog(Boolean knowledgeBlog) {
		this.knowledgeBlog = knowledgeBlog;
	}

    public ProductJourney getJourney() {
        return journey;
    }

    public void setJourney(ProductJourney journey) {
        this.journey = journey;
    }

	public BusinessToCustomerSettings getBusinessToCustomerSettings() {
		return businessToCustomerSettings;
	}

	public void setBusinessToCustomerSettings(BusinessToCustomerSettings businessToCustomerSettings) {
		this.businessToCustomerSettings = businessToCustomerSettings;
	}
}
